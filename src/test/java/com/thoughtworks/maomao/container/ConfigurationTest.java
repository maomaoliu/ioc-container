package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.configurations.Door;
import com.thoughtworks.maomao.stub.configurations.Window;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigurationTest {

    @Test
    public void should_get_wheel_by_wheel_configuration() {
        WheelContainer wheelContainer = new WheelContainer("com.thoughtworks.maomao.stub");
        Door door = wheelContainer.getWheel(Door.class);
        assertNotNull(door);
    }

    @Test (expected = InvalidWheelException.class)
    public void should_throw_exception_if_no_configuration() {
        WheelContainer wheelContainer = new WheelContainer("com.thoughtworks.maomao.stub");
        wheelContainer.getWheel(Window.class);
    }
}
