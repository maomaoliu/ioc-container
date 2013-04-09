package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.base.Stub1;
import com.thoughtworks.maomao.stub.base.invalid.StubWithoutPublicConstructor;
import com.thoughtworks.maomao.stub.base.sub.SubStub1;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class WheelFinderTest_FindWheelClasses {
    @Test
    public void should_load_wheels_in_specific_package() {
        WheelFinder wheelFinder = new WheelFinder("com.thoughtworks.maomao.stub.base.sub");
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(1, wheels.size());
        assertTrue(wheels.contains(SubStub1.class));
    }

    @Test
    public void should_also_load_wheels_in_specific_package_and_sub_packages() {
        WheelFinder wheelFinder = new WheelFinder("com.thoughtworks.maomao.stub.base");
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(23, wheels.size());
        assertTrue(wheels.contains(Stub1.class));
        assertTrue(wheels.contains(SubStub1.class));
        assertTrue(wheels.contains(StubWithoutPublicConstructor.PrivateDefaultConstructor.class));
        assertFalse(wheels.contains(StubWithoutPublicConstructor.InnerStub.class));
    }

    @Test
    public void should_load_nothing_if_there_is_no_class_or_package() {
        WheelFinder wheelFinder = new WheelFinder("com.thoughtworks.maomao.container");
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(0, wheels.size());
    }
}
