package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WheelContainer {
    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();

    public WheelContainer(String packageName) {
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
        if(klazz.getEnclosingClass() != null && !Modifier.isStatic(klazz.getModifiers())){
            return;
        }
        Annotation[] annotations = klazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Wheel.class)) {
                addClass(klazz);
            }
        }
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

    public Set<Class> getWheels() {
        return new HashSet<Class>(implementationMapping.values());
    }

    public Class findImplementation(Class klazz) {
        return implementationMapping.get(klazz);
    }


    public <T> T getWheel(Class<T> klazz) throws InvalidWheelException {
        Class implementationClass = implementationMapping.get(klazz);
        if (implementationClass == null) {
            throw new InvalidWheelException(String.format("Target wheel for %s does not exists", klazz.getName()));
        }
        return createInstance(implementationClass);
    }

    private <T> T createInstance(Class implementationClass) throws InvalidWheelException {
        T wheel = null;
        Constructor targetConstructor = getTargetConstructor(implementationClass);
        Class<?>[] parameterTypes = targetConstructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameterTypes[i];
            parameters[i] = getWheel(parameterType);
        }
        try {
            wheel = (T) targetConstructor.newInstance(parameters);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return wheel;
    }

    private Constructor getTargetConstructor(Class implementationClass) throws InvalidWheelException {
        Constructor<?>[] constructors = implementationClass.getConstructors();
        for (Constructor constructor : constructors) {
            Annotation[] annotations = constructor.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Glue.class)) {
                    return constructor;
                }
            }
        }
        try {
            return implementationClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new InvalidWheelException(e);
        }
    }
}
