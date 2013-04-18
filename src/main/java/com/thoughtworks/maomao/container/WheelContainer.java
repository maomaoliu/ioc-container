package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.web.annotation.Service;

import java.lang.annotation.Annotation;
import java.util.*;

public class WheelContainer {

    private WheelFinder wheelFinder;
    private WheelContainer parent;
    private Loader loader;
    private ConfigurationLoader configurationLoader;
    private Map<Class, WheelInfo> wheelInfoMap = new HashMap<Class, WheelInfo>();
    private HashSet<Class<? extends Annotation>> registeredAnnotations;

    public WheelContainer(String packageName, Class<? extends Annotation>[] annotationTypes) {
        registeredAnnotations = new HashSet<Class<? extends Annotation>>(Arrays.asList(annotationTypes));
        loader = new Loader(packageName, registeredAnnotations);
        wheelFinder = new WheelFinder(loader, registeredAnnotations);
        configurationLoader = new ConfigurationLoader(loader);
        initWheelsInfo(loader);
    }

    public WheelContainer(String packageName, WheelContainer parentContainer, Class<? extends Annotation>[] annotationTypes) {
        this(packageName, annotationTypes);
        parent = parentContainer;
    }

    private void initWheelsInfo(Loader loader) {
        Set<Class> wheelClasses = loader.getClassesByAnnotation(registeredAnnotations);
        for (Class wheelClass : wheelClasses) {
            wheelInfoMap.put(wheelClass, new WheelInfo(wheelClass));
        }
    }

    public <T> T getWheelInstance(Class<T> klazz) {
        if (!configurationLoader.getBeans(klazz).isEmpty()) {
            return configurationLoader.getBeans(klazz).get(0);
        }
        T childInstance = createInstance(klazz);
        if (childInstance != null) {
            return childInstance;
        }
        if (parent == null)
            throw new InvalidWheelException("Wheel not found.");
        return parent.getWheelInstance(klazz);
    }

    private <T> T createInstance(Class<T> klazz) {
        Class childImplementation = wheelFinder.findImplementation(klazz);
        return childImplementation == null ? null : (T) new WheelCreationContext(this).getInstance(getWheelInfo(childImplementation));
    }

    public boolean hasBean(Class klazz) {
        return !configurationLoader.getBeans(klazz).isEmpty();
    }

    public Object getBean(Class setterParameterType) {
        return configurationLoader.getBeans(setterParameterType).get(0);
    }

    public WheelInfo getWheelInfo(Class klazz) {
        WheelInfo wheelInfo = wheelInfoMap.get(klazz);
        if (wheelInfo != null) {
            return wheelInfo;
        }
        return wheelInfoMap.get(wheelFinder.findImplementation(klazz));
    }

    public Set<Class> getWheelClasses(Class klazz) {
        return wheelFinder.getWheelClasses(klazz);
    }

    public Set<Class> getAllClassWithAnnotation(Class<? extends Annotation> annotationType) {
        return loader.getClassesByAnnotation(annotationType);
    }
}
