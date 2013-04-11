package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.configurations.Door;
import com.thoughtworks.maomao.stub.configurations.House;
import com.thoughtworks.maomao.stub.configurations.WheelConfig;
import com.thoughtworks.maomao.stub.configurations.Window;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConfigurationTest {

    private WheelContainer wheelContainer;

    @Before
    public void setUp() throws Exception {
        wheelContainer = new WheelContainer("com.thoughtworks.maomao.stub.configurations");
    }

    @Test
    public void should_get_wheel_by_wheel_configuration() {
        Door door = wheelContainer.getWheel(Door.class);
        assertNotNull(door);
    }

    @Test (expected = InvalidWheelException.class)
    public void should_throw_exception_if_no_configuration() {
        wheelContainer.getWheel(Window.class);
    }

    @Test
    public void should_get_house_by_wheel_configuration() {
        House house = wheelContainer.getWheel(House.class);
        assertEquals(WheelConfig.WOODEN, house.getDoor().getMaterial());
    }
}
