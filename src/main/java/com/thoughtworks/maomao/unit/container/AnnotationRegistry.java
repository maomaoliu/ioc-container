package com.thoughtworks.maomao.unit.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationRegistry {
    private final String path;
    private List<Class<? extends Annotation>> registeredAnnotations = new ArrayList<Class<? extends Annotation>>();

    public AnnotationRegistry(String path) {
        this.path = path;
        doRegister();
    }

    private void doRegister() {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(path);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> klazz = classInfo.load();
                handleClass(klazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClass(Class<?> klazz) {
        if (klazz.isAnnotation()) {
            registeredAnnotations.add((Class<? extends Annotation>) klazz);
        }
    }

    public List<Class<? extends Annotation>> getRegisteredAnnotations() {
        return registeredAnnotations;
    }
}
