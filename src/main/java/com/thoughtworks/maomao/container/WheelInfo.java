package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

public class WheelInfo {

    private Class klazz;
    private boolean hasAnnotatedConstructor;
    private Constructor annotatedConstructor;
    private Set<Class> setterParameterTypes = new HashSet<Class>();
    private Set<Method> setterMethods = new HashSet<Method>();

    public WheelInfo(Class klazz) {
        this.klazz = klazz;
        collectConstructorInfo();
        collectSetterInfo();
    }

    private void collectSetterInfo() {
        if (hasAnnotatedConstructor) {
            return;
        }
        try {
            collectGluedFieldInfo();
            collectGluedSetterInfo();
        } catch (Exception e) {
            throw new InvalidWheelException(e);
        }
    }

    private void collectGluedSetterInfo() throws NoSuchFieldException {
        Method[] methods = klazz.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getAnnotation(Glue.class) != null) {
                Field field = klazz.getDeclaredField(UPPER_CAMEL.to(LOWER_CAMEL, method.getName().replace("set", "")));
                addSetterMethod(method, field);
            }
        }
    }

    private void addSetterMethod(Method method, Field field) {
        checkMethodAndFieldConsistence(field, method);
        checkMethodModifier(method);
        setterParameterTypes.add(field.getType());
        setterMethods.add(method);
    }

    private void collectGluedFieldInfo() throws NoSuchMethodException {
        Field[] fields = klazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getAnnotation(Glue.class) != null) {
                Method method = klazz.getMethod(String.format("set%s", LOWER_CAMEL.to(UPPER_CAMEL, field.getName())), field.getType());
                addSetterMethod(method, field);
            }
        }
    }

    private void checkMethodAndFieldConsistence(Field field, Method method) {
        if (method.getParameterTypes().length != 1 || !field.getType().equals(method.getParameterTypes()[0])) {
            throw new InvalidWheelException("method type is not consistent with filed type");
        }
    }

    private void checkMethodModifier(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new InvalidWheelException("Setter should be public.");
        }
    }

    private void collectConstructorInfo() {
        Constructor<?>[] constructors = klazz.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getAnnotation(Glue.class) != null) {
                if (annotatedConstructor != null) {
                    throw new InvalidWheelException("More than one Glued constructor");
                }
                annotatedConstructor = constructor;
                hasAnnotatedConstructor = true;
            }
        }
    }

    public boolean hasAnnotatedConstructor() {
        return hasAnnotatedConstructor;
    }

    public Constructor getConstructor() {
        try {
            return hasAnnotatedConstructor ? annotatedConstructor : klazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new InvalidWheelException(e);
        }
    }

    public Class getWheelClass() {
        return klazz;
    }

    public Set<Class> getSetterParameterTypes() {
        return setterParameterTypes;
    }

    public Class<Object>[] getConstructorParameterTypes() {
        return hasAnnotatedConstructor ? annotatedConstructor.getParameterTypes() : new Class[0];
    }

    public Set<Method> getSetterMethods() {
        return setterMethods;
    }
}
