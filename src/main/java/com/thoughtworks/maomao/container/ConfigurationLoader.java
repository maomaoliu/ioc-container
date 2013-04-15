package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Bean;
import com.thoughtworks.maomao.annotations.Configuration;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationLoader {

    private Map<Class, List> beanMapping = new HashMap<Class, List>();

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

    private void addBean(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> type = method.getReturnType();
        Object instance = method.invoke(object);
        List instances = beanMapping.get(type);
        if (instances == null) {
            instances = new ArrayList();
        }
        instances.add(instance);
        beanMapping.put(type, instances);
    }

    public Map<Class, List> getBeanMapping() {
        return beanMapping;
    }

    public <T> List<T> getBeans(Class<T> klass) {
        List list = beanMapping.get(klass);
        return list == null ? new ArrayList<T>() : list;
    }
}
