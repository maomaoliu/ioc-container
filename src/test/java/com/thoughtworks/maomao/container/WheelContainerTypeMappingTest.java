package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.base.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WheelContainerTypeMappingTest {

    private WheelFinder wheelFinder;

    @Before
    public void setUp() throws Exception {
        wheelFinder = new WheelFinder("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_find_class_by_class() {
        Class implementationClass = wheelFinder.findImplementation(Stub1.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface() {
        Class implementationClass = wheelFinder.findImplementation(StubInterface.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractStub.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class_of_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractParentStub.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface_of_super_class() {
        Class implementationClass = wheelFinder.findImplementation(AbstractStubInterface.class);
        assertEquals(Stub1.class, implementationClass);
    }
}
