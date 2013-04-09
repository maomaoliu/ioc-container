package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Configuration;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class WheelContainer {

    private static final String ANNOTATIONS_DIR = "com.thoughtworks.maomao.annotations";

    private final List<Class<? extends Annotation>> registeredAnnotations;

    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();
    private Map<Class, List<Class>> annotationMapping = new HashMap<Class, List<Class>>();
    private Map<Class, List> initBeans;
    private WheelContainer parent;

    public WheelContainer(String packageName) {
        registeredAnnotations = new AnnotationRegistry(ANNOTATIONS_DIR).getRegisteredAnnotations();
        findWheels(packageName);
        getInitBeans();
    }

    private void getInitBeans() {
        List<Class> configurationClasses = annotationMapping.get(Configuration.class);
        initBeans = new ConfigurationLoader(configurationClasses).getBeans();
    }

    private void findWheels(String packageName) {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> klazz = classInfo.load();
                handleClass(klazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClass(Class<?> klazz) {
        if (isNotPublicClass(klazz) || isNonStaticInnerClass(klazz)) {
            return;
        }
        Annotation[] annotations = klazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (registeredAnnotations.contains(annotation.annotationType())) {
                List<Class> classes = annotationMapping.get(annotation.annotationType());
                if (classes == null) {
                    classes = new ArrayList<Class>();
                }
                classes.add(klazz);
                annotationMapping.put(annotation.annotationType(), classes);
                addClass(klazz);
            }
        }
        Class<?>[] innerClasses = klazz.getClasses();
        for (Class innerClass : innerClasses) {
            handleClass(innerClass);
        }
    }

    private boolean isNotPublicClass(Class<?> klazz) {
        return !Modifier.isPublic(klazz.getModifiers());
    }

    private boolean isNonStaticInnerClass(Class<?> klazz) {
        return klazz.getEnclosingClass() != null && !Modifier.isStatic(klazz.getModifiers());
    }

    private void addClass(Class<?> klazz) {
        implementationMapping.put(klazz, klazz);
        handleInterfacesAndSuperClass(klazz, klazz);
    }

    private void handleInterfacesAndSuperClass(Class<?> superKlazz, Class<?> klazz) {
        Class<?>[] interfaces = superKlazz.getInterfaces();
        for (Class aInterface : interfaces) {
            implementationMapping.put(aInterface, klazz);
        }
        Class<?> superclass = superKlazz.getSuperclass();
        if (superclass != null) {
            implementationMapping.put(superclass, klazz);
            handleInterfacesAndSuperClass(superclass, klazz);
        }
    }

    public Set<Class> getWheelClasses() {
        return new HashSet<Class>(implementationMapping.values());
    }

    public Class findImplementation(Class klazz) {
        return implementationMapping.get(klazz);
    }

    public <T> T getWheel(Class<T> klazz) {
        Class implementationClass = implementationMapping.get(klazz);
        if (implementationClass == null) {
            throw new InvalidWheelException(String.format("Target wheel for %s does not exists", klazz.getName()));
        }
        if (initBeans.get(klazz) != null) {
            return (T) initBeans.get(klazz).get(0);
        }
        return createInstance(implementationClass);
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

    public List<Class> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        return annotationMapping.get(annotation);
    }

    public void setParent(WheelContainer parent) {
        this.parent = parent;
        this.implementationMapping.putAll(parent.implementationMapping);
        this.annotationMapping.putAll(parent.annotationMapping);
        this.initBeans.putAll(parent.initBeans);
    }

    public WheelContainer getParent() {
        return parent;
    }
}
