package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Wheel;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class WheelContainer {
    private List<Class> wheels;

    public WheelContainer(String packageName) {
        wheels = new ArrayList<Class>();
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : topLevelClasses) {
                Class<?> klazz = classInfo.load();
                Annotation[] annotations = klazz.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(Wheel.class)) {
                        wheels.add(klazz);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Class> getWheels() {
        return wheels;
    }
}
