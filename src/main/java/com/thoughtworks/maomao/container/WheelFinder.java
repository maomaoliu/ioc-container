package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Configuration;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;

public class WheelFinder {
    private static final String ANNOTATIONS_DIR = "com.thoughtworks.maomao.annotations";

    private final List<Class<? extends Annotation>> registeredAnnotations;
    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();
    private Map<Class, List<Class>> annotationMapping = new HashMap<Class, List<Class>>();

    public WheelFinder(String packageName) {
        registeredAnnotations = new AnnotationRegistry(ANNOTATIONS_DIR).getRegisteredAnnotations();
        findWheels(packageName);
    }

    public Set<Class> getWheelClasses() {
        return new HashSet<Class>(implementationMapping.values());
    }

    public List<Class> getConfigClasses() {
        return annotationMapping.get(Configuration.class);
    }

    public Class findImplementation(Class klazz) {
        return implementationMapping.get(klazz);
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

}
