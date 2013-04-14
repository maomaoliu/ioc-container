package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.unit.annotations.Wheel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WheelContainer {

    private WheelFinder wheelFinder;
    private WheelContainer parent;
    private Loader loader;
    private ConfigurationLoader configurationLoader;
    private Map<Class, WheelInfo> wheelInfoMap = new HashMap<Class, WheelInfo>();

    public WheelContainer(String packageName) {
        loader = new Loader(packageName);
        wheelFinder = new WheelFinder(loader);
        configurationLoader = new ConfigurationLoader(loader);
        initWheelsInfo(loader);
    }

    private void initWheelsInfo(Loader loader) {
        List<Class> wheelClasses = loader.getClassesByAnnotation(Wheel.class);
        for (Class wheelClass : wheelClasses) {
            wheelInfoMap.put(wheelClass, new WheelInfo(wheelClass));
        }
    }

    public WheelContainer(String packageName, WheelContainer parentContainer) {
        this(packageName);
        parent = parentContainer;
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
        return wheelInfoMap.get(klazz);
    }
}
