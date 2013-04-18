package com.thoughtworks.maomao.unit.container;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.Loader;
import com.thoughtworks.maomao.container.WheelFinder;
import com.thoughtworks.maomao.stub.base.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class WheelFinderTest_WheelContainerTypeMapping {

    private WheelFinder wheelFinder;

    @Before
    public void setup() throws Exception {
        Set<Class<? extends Annotation>> registeredAnnotations = new HashSet<Class<? extends Annotation>>();
        registeredAnnotations.add(Wheel.class);
        Loader loader = new Loader("com.thoughtworks.maomao.stub", registeredAnnotations);
        wheelFinder = new WheelFinder(loader, registeredAnnotations);
    }

    @Test
    public void should_find_class_by_class() {
        Class implementationClass = wheelFinder.findImplementation(Stub.class);
        assertEquals(Stub.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface() {
        Class implementationClass = wheelFinder.findImplementation(StubInterface.class);
        assertEquals(Stub.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractStub.class);
        assertEquals(Stub.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class_of_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractParentStub.class);
        assertEquals(Stub.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface_of_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractStubInterface.class);
        assertEquals(Stub.class, implementationClass);
    }
}
