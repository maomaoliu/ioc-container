package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class WheelCreator {
    private WheelContainer wheelContainer;

    public WheelCreator(WheelContainer wheelContainer) {
        this.wheelContainer = wheelContainer;
    }

    public <T> T createInstance(Class implementationClass) {
        Constructor targetConstructor = getTargetConstructor(implementationClass);
        if (targetConstructor.getAnnotation(Glue.class) == null) {
            return createInstanceBySetter(targetConstructor);
        }
        return createInstanceByConstructor(targetConstructor);
    }

    private <T> T createInstanceByConstructor(Constructor targetConstructor) {
        Class<?>[] parameterTypes = targetConstructor.getParameterTypes();
        Object[] parameters = getParameters(parameterTypes);
        try {
            return (T) targetConstructor.newInstance(parameters);
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
    }

    private <T> T createInstanceBySetter(Constructor targetConstructor) {
        try {
            T result = (T) targetConstructor.newInstance();
            handleAnnotatedMethods(result);
            handleAnnotatedFields(result);
            return result;
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
    }

    private <T> void handleAnnotatedFields(T result) throws Exception {
        Field[] fields = result.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(Glue.class) != null) {
                invokeSetter(result, field);
            }
        }
    }

    private <T> void handleAnnotatedMethods(T result) throws Exception {
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(Glue.class) != null) {
                invokeSetter(result, method);
            }
        }
    }

    private <T> void invokeSetter(T result, Field field) throws Exception {
        Method method = result.getClass().getMethod(String.format("set%s", LOWER_CAMEL.to(UPPER_CAMEL, field.getName())), field.getType());
        checkMethodModifier(method);
        invokeMethod(result, method);
    }

    private <T> void invokeSetter(T result, Method method) throws Exception {
        checkMethodModifier(method);
        Field field = result.getClass().getDeclaredField(UPPER_CAMEL.to(LOWER_CAMEL, method.getName().replace("set", "")));
        if (field == null) {
            throw new InvalidWheelException("Field does not exist.");
        }
        invokeMethod(result, method);
    }

    private void checkMethodModifier(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new InvalidWheelException("Setter should be public.");
        }
    }

    private <T> void invokeMethod(T result, Method method) throws Exception {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = getParameters(parameterTypes);
        method.invoke(result, parameters);
    }

    private Object[] getParameters(Class<?>[] parameterTypes) {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameters.length; i++) {
            Class parameterType = parameterTypes[i];
            parameters[i] = wheelContainer.getWheel(parameterType);
        }
        return parameters;
    }

    private Constructor getTargetConstructor(Class implementationClass) {
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

    private Constructor getTargetConstructorWithAnnotation(Constructor<?>[] constructors) {
        Constructor targetConstructor = null;
        for (Constructor constructor : constructors) {
            if (constructor.getAnnotation(Glue.class) != null) {
                if (targetConstructor != null) {
                    throw new InvalidWheelException("More than one Glued constructor");
                }
                targetConstructor = constructor;
            }
        }
        return targetConstructor;
    }
}
