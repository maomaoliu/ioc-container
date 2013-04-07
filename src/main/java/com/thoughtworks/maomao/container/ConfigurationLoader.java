package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class ConfigurationLoader {

    private List<Class> configurationClasses;
    private Map<Class, List> beans = new HashMap<Class, List>();

    public ConfigurationLoader(List<Class> configurationClasses) {
        this.configurationClasses = configurationClasses;
        if (configurationClasses == null) return;
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
