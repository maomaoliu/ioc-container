package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;

public class WheelContainer {

    private WheelFinder wheelFinder;
    private WheelContainer parent;
    private Loader loader;
    private WheelCreator wheelCreator;
    private ConfigurationLoader configurationLoader;

    public WheelContainer(String packageName) {
        loader = new Loader(packageName);
        wheelFinder = new WheelFinder(loader);
        configurationLoader = new ConfigurationLoader(loader);
        this.wheelCreator = new WheelCreator(this);
    }

    public WheelContainer(String packageName, WheelContainer parentContainer) {
        this(packageName);
        parent = parentContainer;
    }

    public <T> T getWheel(Class<T> klazz) {
        checkType(klazz);
        if (!configurationLoader.getBeans(klazz).isEmpty()) {
            return (T) configurationLoader.getBeans(klazz).get(0);
        }
        T childInstance = createInstance(klazz);
        return childInstance == null ? parent.getWheel(klazz) : childInstance;
    }

    private <T> T createInstance(Class<T> klazz) {
        Class childImplementation = wheelFinder.findImplementation(klazz);
        return childImplementation == null ? null : (T) wheelCreator.createInstance(childImplementation);
    }

    private <T> void checkType(Class<T> klazz) {
        if (wheelFinder.findImplementation(klazz) == null) {
            if (parent == null) {
                throw new InvalidWheelException(String.format("Target wheel for %s does not exists", klazz.getName()));
            }
            parent.checkType(klazz);
        }
    }
}
