package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Bean;
import com.thoughtworks.maomao.annotations.Configuration;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationLoader {

    private Map<Class, List> beans = new HashMap<Class, List>();

    public ConfigurationLoader(Loader loader) {
        for (Class klazz : loader.getClassesByAnnotation(Configuration.class)) {
            loadBeans(klazz);
        }
    }

    private void loadBeans(Class klazz) {
        try {
            Object object = klazz.newInstance();
            Method[] methods = klazz.getMethods();
            for (Method method : methods) {
                if (method.getAnnotation(Bean.class) != null) {
                    addBean(method, object);
                }
            }
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
    }

    //todo: check method modifier
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
