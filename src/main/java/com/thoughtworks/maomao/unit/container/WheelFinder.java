package com.thoughtworks.maomao.unit.container;

import com.thoughtworks.maomao.unit.annotations.Wheel;

import java.util.*;

public class WheelFinder {

    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();

    public WheelFinder(Loader loader) {
        findWheels(loader.getClassesByAnnotation(Wheel.class));
    }

    private void findWheels(List<Class> wheelClasses) {
        for (Class wheel : wheelClasses) {
            addClass(wheel);
        }
    }

    public Set<Class> getWheelClasses() {
        return new HashSet<Class>(implementationMapping.values());
    }

    public Class findImplementation(Class klazz) {
        return implementationMapping.get(klazz);
    }

    private void addClass(Class<?> klazz) {
        implementationMapping.put(klazz, klazz);
        handleInterfacesAndSuperClass(klazz, klazz);
    }

    private void handleInterfacesAndSuperClass(Class<?> superKlazz, Class<?> klazz) {
        Class<?>[] interfaces = superKlazz.getInterfaces();
        for (Class aInterface : interfaces) {
            implementationMapping.put(aInterface, klazz);
        }
        Class<?> superclass = superKlazz.getSuperclass();
        if (superclass != null) {
            implementationMapping.put(superclass, klazz);
            handleInterfacesAndSuperClass(superclass, klazz);
        }
    }

}
