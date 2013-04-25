package com.thoughtworks.maomao.unit.container;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.ConfigurationLoader;
import com.thoughtworks.maomao.container.Loader;
import com.thoughtworks.maomao.container.WheelFinder;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.invalid.StubWithoutPublicConstructor;
import com.thoughtworks.maomao.stub.base.sub.SubStub;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class WheelFinderTest_FindWheelClasses {

    private Set<Class<? extends Annotation>> registeredAnnotations;

    @Before
    public void setup() throws Exception {
        registeredAnnotations = new HashSet<Class<? extends Annotation>>();
        registeredAnnotations.add(Wheel.class);
    }

    @Test
    public void should_load_wheels_in_specific_package() {
        Loader loader = new Loader("com.thoughtworks.maomao.stub.base.sub", registeredAnnotations);
        WheelFinder wheelFinder = new WheelFinder(loader, registeredAnnotations);
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(1, wheels.size());
        assertTrue(wheels.contains(SubStub.class));
    }

    @Test
    public void should_also_load_wheels_in_specific_package_and_sub_packages() {
        Loader loader = new Loader("com.thoughtworks.maomao.stub.base", registeredAnnotations);
        WheelFinder wheelFinder = new WheelFinder(loader, registeredAnnotations);
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(9, wheels.size());
        assertTrue(wheels.contains(Stub.class));
        assertTrue(wheels.contains(SubStub.class));
        assertFalse(wheels.contains(StubWithoutPublicConstructor.PrivateDefaultConstructor.class));
        assertFalse(wheels.contains(StubWithoutPublicConstructor.InnerStub.class));
    }

    @Test
    public void should_load_nothing_if_there_is_no_class_or_package() {
        Loader loader = new Loader("com.thoughtworks.maomao.unit.container", registeredAnnotations);
        WheelFinder wheelFinder = new WheelFinder(loader, registeredAnnotations);
        Set<Class> wheels = wheelFinder.getWheelClasses();
        assertEquals(0, wheels.size());
    }
}
