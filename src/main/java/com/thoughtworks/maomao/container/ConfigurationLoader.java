package com.thoughtworks.maomao.container;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.maomao.annotations.Bean;
import com.thoughtworks.maomao.annotations.Configuration;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationLoader {

    private List<Class> configurationClasses = new ArrayList<Class>();
    private Map<Class, List> beans = new HashMap<Class, List>();

    public ConfigurationLoader(String packageName) {
        try {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassPath.ClassInfo> allClasses = classPath.getTopLevelClassesRecursive(packageName);
            for (ClassPath.ClassInfo classInfo : allClasses) {
                Class<?> klazz = classInfo.load();
                Annotation[] annotations = klazz.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(Configuration.class)) {
                        configurationClasses.add(klazz);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Class klazz : configurationClasses) {
            loadBeans(klazz);
        }
    }

    private void loadBeans(Class klazz) {
        try {
            Object object = klazz.newInstance();
            Method[] methods = klazz.getMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Bean.class))
                        addBean(method, object);
                }
            }
        } catch (InstantiationException e) {
            throw new InvalidWheelException(e);
        } catch (IllegalAccessException e) {
            throw new InvalidWheelException(e);
        } catch (InvocationTargetException e) {
            throw new InvalidWheelException(e);
        }
    }

    private void addBean(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> type = method.getReturnType();
        TypeVariable<Method>[] typeParameters = method.getTypeParameters();
        Object instance = method.invoke(object, typeParameters);
        List instances = beans.get(type);
        if (instances == null) {
            instances = new ArrayList();
        }
        instances.add(instance);
        beans.put(type, instances);
    }

    public Map<Class, List> getBeans() {
        return beans;
    }
}
