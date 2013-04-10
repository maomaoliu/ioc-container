package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WheelContainer {

    private WheelFinder wheelFinder;
    private Map<Class, List> initBeanInstances;
    private WheelContainer parent;
    private Loader loader;
    private WheelCreator wheelCreator;

    public WheelContainer(String packageName) {
        loader = new Loader(packageName);
        wheelFinder = new WheelFinder(loader);
        initBeanInstances = new ConfigurationLoader(loader).getBeans();
        this.wheelCreator = new WheelCreator(this);
    }

    public WheelContainer(String packageName, WheelContainer parentContainer) {
        this(packageName);
        parent = parentContainer;
    }

    public <T> T getWheel(Class<T> klazz) {
        checkType(klazz);
        if (initBeanInstances.get(klazz) != null) {
            return (T) initBeanInstances.get(klazz).get(0);
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
