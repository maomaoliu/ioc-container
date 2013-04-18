package com.thoughtworks.maomao.container;

import java.lang.annotation.Annotation;
import java.util.*;

public class WheelFinder {

    private Map<Class, Class> implementationMapping = new HashMap<Class, Class>();

    public WheelFinder(Loader loader, Set<Class<? extends Annotation>> registeredAnnotations) {
        findWheels(loader.getClassesByAnnotation(registeredAnnotations));
    }

    private void findWheels(Set<Class> wheelClasses) {
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

    public Set<Class> getWheelClasses(Class klazz) {
        Set<Class> allWheelClasses = getWheelClasses();
        Set<Class> result = new HashSet<Class>();
        for (Class wheelClass : allWheelClasses) {
            Class<?>[] interfaces = wheelClass.getInterfaces();
            if (Arrays.asList(interfaces).contains(klazz) || getAllSuperClasses(wheelClass).contains(klazz)) {
                result.add(wheelClass);
            }
        }
        return result;
    }

    private Set<Class> getAllSuperClasses(Class klazz) {
        Set<Class> result = new HashSet<Class>();
        Class c = klazz;
        while (c != null) {
            result.add(c);
            c = c.getSuperclass();
        }
        return result;
    }
}
