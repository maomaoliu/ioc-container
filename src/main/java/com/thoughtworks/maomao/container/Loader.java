package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.google.common.reflect.ClassPath.ClassInfo;
import static com.google.common.reflect.ClassPath.from;

public class Loader {
    private static final String ANNOTATIONS_DIR = "com.thoughtworks.maomao.annotations";

    private List<Class<? extends Annotation>> registeredAnnotations;
    private Map<Class<? extends Annotation>, Set<Class>> annotationMapping = new HashMap<Class<? extends Annotation>, Set<Class>>();

    public Loader(String packageName, Set<Class<? extends Annotation>> registeredAnnotations) {
        this.registeredAnnotations = new AnnotationRegistry(ANNOTATIONS_DIR).getRegisteredAnnotations();
        this.registeredAnnotations.addAll(registeredAnnotations);
        loadClasses(packageName);
    }

    public Set<Class> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class> classes = annotationMapping.get(annotation);
        return classes == null ? new HashSet<Class>() : classes;
    }

    public Set<Class> getClassesByAnnotation(Set<Class<? extends Annotation>> annotationTypes) {
        Set<Class> result = new HashSet<Class>();
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            Set<Class> classes = annotationMapping.get(annotationType);
            if (classes != null) {
                result.addAll(classes);
            }
        }
        return result;
    }

    private void loadClasses(String packageName) {
        try {
            ClassPath classPath = from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(packageName);
            for (ClassInfo classInfo : allClasses) {
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
                Set<Class> classes = annotationMapping.get(annotation.annotationType());
                if (classes == null) {
                    classes = new HashSet<Class>();
                }
                classes.add(klazz);
                annotationMapping.put(annotation.annotationType(), classes);
            }
        }
        handleInnerClasses(klazz);
    }

    private void handleInnerClasses(Class<?> klazz) {
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


}
