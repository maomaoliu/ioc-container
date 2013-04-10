package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class WheelContainer {

    private WheelFinder wheelFinder;
    private Map<Class, List> initBeans;
    private WheelContainer parent;
    private Loader loader;

    public WheelContainer(String packageName) {
        loader = new Loader(packageName);
        wheelFinder = new WheelFinder(loader);
        initBeans = new ConfigurationLoader(loader).getBeans();
    }

    public WheelContainer(String packageName, WheelContainer parentContainer) {
        this(packageName);
        parent = parentContainer;
    }

    public Set<Class> getWheelClasses() {
        Set<Class> wheelClasses = wheelFinder.getWheelClasses();
        if (parent != null)
            wheelClasses.addAll(parent.getWheelClasses());
        return wheelClasses;
    }

    public <T> T getWheel(Class<T> klazz) {
        Class implementationClass = findImplementation(klazz);
        if (implementationClass == null) {
            throw new InvalidWheelException(String.format("Target wheel for %s does not exists", klazz.getName()));
        }

        if (initBeans.get(klazz) != null) {
            return (T) initBeans.get(klazz).get(0);
        }

        Class childImplementation = wheelFinder.findImplementation(klazz);
        T childInstance = null;
        if (childImplementation != null) {
            childInstance = createInstance(childImplementation);
        }
        if(childInstance!=null){
            return childInstance;
        }
        return parent.getWheel(klazz);
    }

    private <T> Class findImplementation(Class<T> klazz) {
        Class implementation = wheelFinder.findImplementation(klazz);
        if (implementation != null)
            return implementation;
        if (parent != null)
            return parent.wheelFinder.findImplementation(klazz);
        return null;
    }

    private <T> T createInstance(Class implementationClass) {
        Constructor targetConstructor = getTargetConstructor(implementationClass);
        if (targetConstructor.getAnnotation(Glue.class) == null) {
            try {
                T result = (T) targetConstructor.newInstance();
                Method[] methods = result.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    method.setAccessible(true);
                    if (method.getAnnotation(Glue.class) != null) {
                        if (!Modifier.isPublic(method.getModifiers())) {
                            throw new InvalidWheelException("Setter should be public.");
                        }
                        Field field = result.getClass().getDeclaredField(UPPER_CAMEL.to(LOWER_CAMEL, method.getName().replace("set", "")));
                        if (field == null) {
                            throw new InvalidWheelException("Field does not exist.");
                        }
                        invokeMethod(result, method);
                    }
                }
                Field[] fields = result.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getAnnotation(Glue.class) != null) {
                        Method method = result.getClass().getMethod(String.format("set%s", LOWER_CAMEL.to(UPPER_CAMEL, field.getName())), field.getType());
                        if (!Modifier.isPublic(method.getModifiers())) {
                            throw new InvalidWheelException("Setter should be public.");
                        }
                        invokeMethod(result, method);
                    }
                }
                return result;
            } catch (Exception e) {
                throw new InvalidWheelException(e);
            }
        } else {
            Class<?>[] parameterTypes = targetConstructor.getParameterTypes();
            Object[] parameters = getParameters(parameterTypes);
            try {
                return (T) targetConstructor.newInstance(parameters);
            } catch (Exception e) {
                throw new InvalidWheelException(e);
            }
        }
    }

    private <T> void invokeMethod(T result, Method method) throws IllegalAccessException, InvocationTargetException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = getParameters(parameterTypes);
        method.invoke(result, parameters);
    }

    private Object[] getParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameterTypes[i];
            parameters[i] = getWheel(parameterType);
        }
        return parameters;
    }

    private Constructor getTargetConstructor(Class implementationClass) {
        Constructor<?>[] constructors = implementationClass.getConstructors();
        Constructor targetConstructor = getTargetConstructorWithAnnotation(constructors);
        if (targetConstructor != null) {
            return targetConstructor;
        }
        try {
            return implementationClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new InvalidWheelException(e);
        }
    }

    private Constructor getTargetConstructorWithAnnotation(Constructor<?>[] constructors) {
        Constructor targetConstructor = null;
        for (Constructor constructor : constructors) {
            Annotation[] annotations = constructor.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Glue.class)) {
                    if (targetConstructor != null) {
                        throw new InvalidWheelException("More than one Glued constructor");
                    }
                    targetConstructor = constructor;
                }
            }
        }
        return targetConstructor;
    }
}
