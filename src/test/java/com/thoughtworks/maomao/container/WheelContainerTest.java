package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.Stub1;
import com.thoughtworks.maomao.stub.sub.SubStub1;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WheelContainerTest {
    @Test
    public void should_load_wheels_in_specific_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub.sub");
        Set<Class> wheels = container.getWheels();
        assertEquals(wheels.size(), 1);
        assertTrue(wheels.contains(SubStub1.class));
    }

    @Test
    public void should_also_load_wheels_in_specific_package_and_sub_packages() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Set<Class> wheels = container.getWheels();
        assertEquals(wheels.size(), 6);
        assertTrue(wheels.contains(Stub1.class));
        assertTrue(wheels.contains(SubStub1.class));
    }

    @Test
    public void should_load_nothing_if_there_is_no_class_or_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.container");
        Set<Class> wheels = container.getWheels();
        assertEquals(wheels.size(), 0);
    }
}
