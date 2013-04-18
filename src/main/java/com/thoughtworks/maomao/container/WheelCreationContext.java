package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WheelCreationContext {

    private WheelContainer wheelContainer;

    private Map<Class, Object> map = new HashMap<Class, Object>();

    public WheelCreationContext(WheelContainer wheelContainer) {
        this.wheelContainer = wheelContainer;
    }

    public Object getInstance(WheelInfo wheelInfo) {
        try {
            collectInfo(wheelInfo);
            if (map.containsValue(null)) {
                throw new InvalidWheelException("could not create wheel");
            }
            for (Object instance : map.values()) {
                glueForInstance(instance);
            }
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
        return map.get(wheelInfo.getWheelClass());
    }

    private void glueForInstance(Object instance) throws Exception {
        if (wheelContainer.hasBean(instance.getClass())) {
            return;
        }
        WheelInfo wheelInfo = wheelContainer.getWheelInfo(instance.getClass());
        Set<Method> setterMethods = wheelInfo.getSetterMethods();
        for (Method setterMethod : setterMethods) {
            Set<Class> parameterTypeClasses = wheelContainer.getWheelClasses(setterMethod.getParameterTypes()[0]);
            for (Class parameterTypeClass : parameterTypeClasses) {
                if (map.get(parameterTypeClass) != null) {
                    setterMethod.invoke(instance, map.get(parameterTypeClass));
                    break;
                }
            }
        }
    }

    private void collectInfo(WheelInfo wheelInfo) throws Exception {
        if (map.get(wheelInfo.getWheelClass()) != null) {
            return;
        }
        if (wheelInfo.hasAnnotatedConstructor()) {
            if (!tryToCreateInstance(wheelInfo.getWheelClass())){
                collectParametersInfo(wheelInfo.getConstructorParameterTypes());
            }
        } else {
            Object instance = wheelInfo.getConstructor().newInstance();
            addInstance(wheelInfo.getWheelClass(), instance);
            Set<Class> setterParameterTypes = wheelInfo.getSetterParameterTypes();
            collectParametersInfo(setterParameterTypes.toArray(new Class[setterParameterTypes.size()]));
        }
    }

    private void collectParametersInfo(Class[] setterParameterTypes) throws Exception {
        for (Class setterParameterType : setterParameterTypes) {
            if (map.containsKey(setterParameterType)) {
                continue;
            }
            if (wheelContainer.hasBean(setterParameterType)) {
                addInstance(setterParameterType, wheelContainer.getBean(setterParameterType));
            } else {
                collectInfo(wheelContainer.getWheelInfo(setterParameterType));
            }
        }
    }

    private void addInstance(Class klazz, Object instance) throws Exception {
        map.put(klazz, instance);
        Set<Class> keySet = map.keySet();
        for (Class key : keySet) {
            if (map.get(key) == null) {
                tryToCreateInstance(key);
            }
        }
    }

    private boolean tryToCreateInstance(Class klazz) throws Exception {
        WheelInfo wheelInfo = wheelContainer.getWheelInfo(klazz);
        Class<Object>[] constructorParameterTypes = wheelInfo.getConstructorParameterTypes();
        Object[] parameters = new Object[constructorParameterTypes.length];
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            Class<Object> constructorParameterType = constructorParameterTypes[i];
            if (map.get(constructorParameterType) == null) {
                map.put(klazz, null);
                return false;
            }
            parameters[i] = map.get(constructorParameterType);
        }
        map.put(klazz, wheelInfo.getConstructor().newInstance(parameters));
        return true;
    }
}
