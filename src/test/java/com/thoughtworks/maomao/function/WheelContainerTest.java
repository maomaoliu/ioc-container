package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.annotations.AnotherWheel;
import com.thoughtworks.maomao.stub.base.AbstractStub;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.StubInterface;
import com.thoughtworks.maomao.stub.others.OtherStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WheelContainerTest {
    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub", new Class[]{Wheel.class, AnotherWheel.class});
    }

    @Test
    public void should_create_general_class() {
        assertNotNull(container.getWheelInstance(Stub.class));
    }

    @Test
    public void should_create_general_class_with_registered_annotation() {
        assertNotNull(container.getWheelInstance(OtherStub.class));
    }

    @Test
    public void should_create_abstract_class() {
        assertNotNull(container.getWheelInstance(AbstractStub.class));
    }

    @Test
    public void should_create_interface() {
        assertNotNull(container.getWheelInstance(StubInterface.class));
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_no_wheel() {
        assertNotNull(container.getWheelInstance(Assert.class));
    }

}
