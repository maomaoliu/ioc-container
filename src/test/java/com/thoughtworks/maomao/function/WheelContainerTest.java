package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.unit.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.base.AbstractStub;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.StubInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WheelContainerTest {
    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_create_general_class() {
        assertNotNull(container.getWheel(Stub.class));
    }

    @Test
    public void should_create_abstract_class() {
        assertNotNull(container.getWheel(AbstractStub.class));
    }

    @Test
    public void should_create_interface() {
        assertNotNull(container.getWheel(StubInterface.class));
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_no_wheel() {
        assertNotNull(container.getWheel(Assert.class));
    }

}
