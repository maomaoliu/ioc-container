package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.annotations.Wheel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WheelContainerAnnotationMappingTest {

    private WheelContainer container;

    @Test
    public void shouldFindClassesByAnnotation() {
        container = new WheelContainer("com.thoughtworks.maomao.stub.base");
        List<Class> classes = container.getClassesByAnnotation(Wheel.class);
        assertEquals(14, classes.size());
    }
}
