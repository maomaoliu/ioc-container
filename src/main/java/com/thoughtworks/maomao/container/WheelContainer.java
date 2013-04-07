package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

public class WheelContainer {

    private static final String ANNOTATIONS_DIR = "com.thoughtworks.maomao.annotations";

    private final List<Class<? extends Annotation>> registeredAnnotations;

    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();
    private Map<Class, List<Class>> annotationMapping = new HashMap<Class, List<Class>>();

    public WheelContainer(String packageName) {
        registeredAnnotations = new AnnotationRegistry(ANNOTATIONS_DIR).getRegisteredAnnotations();
        findWheels(packageName);
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
        if(isNotPublicClass(klazz) || isNonStaticInnerClass(klazz)){
            return;
        }
        Annotation[] annotations = klazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if(registeredAnnotations.contains(annotation.annotationType())){
                List<Class> classes = annotationMapping.get(annotation.annotationType());
                if (classes == null){
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
        Constructor targetConstructor = getTargetConstructor(implementationClass);
        Class<?>[] parameterTypes = targetConstructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameterTypes[i];
            parameters[i] = getWheel(parameterType);
        }
        try {
            return (T) targetConstructor.newInstance(parameters);
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
    }

    private Constructor getTargetConstructor(Class implementationClass) throws InvalidWheelException {
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

    private Constructor getTargetConstructorWithAnnotation(Constructor<?>[] constructors) throws InvalidWheelException {
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
}
