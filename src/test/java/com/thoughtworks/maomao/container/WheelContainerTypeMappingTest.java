package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.AbstractStub;
import com.thoughtworks.maomao.stub.Stub1;
import com.thoughtworks.maomao.stub.StubInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WheelContainerTypeMappingTest {
    @Test
    public void should_find_class_by_class() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Class implementationClass = container.findImplementation(Stub1.class);
        assertEquals(implementationClass, Stub1.class);
    }

    @Test
    public void should_find_class_by_interface() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Class implementationClass = container.findImplementation(StubInterface.class);
        assertEquals(implementationClass, Stub1.class);
    }

    @Test
    public void should_find_class_by_super_class() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Class implementationClass = container.findImplementation(AbstractStub.class);
        assertEquals(implementationClass, Stub1.class);
    }
}
