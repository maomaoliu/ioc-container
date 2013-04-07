package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.*;
import com.thoughtworks.maomao.stub.base.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WheelContainerTypeMappingTest {

    private WheelContainer container;

    @Before
    public void setUp() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_find_class_by_class() {
        Class implementationClass = container.findImplementation(Stub1.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface() {
        Class implementationClass = container.findImplementation(StubInterface.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class() {
        Class implementationClass = container.findImplementation(AbstractStub.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_super_class_of_super_class() {
        Class implementationClass = container.findImplementation(AbstractParentStub.class);
        assertEquals(Stub1.class, implementationClass);
    }

    @Test
    public void should_find_class_by_interface_of_super_class() {
        Class implementationClass = container.findImplementation(AbstractStubInterface.class);
        assertEquals(Stub1.class, implementationClass);
    }
}
