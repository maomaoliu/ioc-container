package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.configurations.WheelConfig;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WheelFinderTest_FindConfigClasses {
    @Test
    public void should_load_configuration_in_specific_package() {
        WheelFinder wheelFinder = new WheelFinder("com.thoughtworks.maomao.stub.configurations");
        List<Class> configClasses = wheelFinder.getConfigClasses();
        assertEquals(1, configClasses.size());
        assertTrue(configClasses.contains(WheelConfig.class));
    }

    @Test
    public void should_load_no_configuration_in_specific_package() {
        WheelFinder wheelFinder = new WheelFinder("com.thoughtworks.maomao.stub.annotations");
        List<Class> configClasses = wheelFinder.getConfigClasses();
        assertNull(configClasses);
    }
}
