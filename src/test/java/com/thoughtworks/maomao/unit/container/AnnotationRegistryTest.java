package com.thoughtworks.maomao.unit.container;

import com.thoughtworks.maomao.container.AnnotationRegistry;
import com.thoughtworks.maomao.stub.annotations.AnotherWheel;
import com.thoughtworks.maomao.stub.annotations.StubAnnotation;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class AnnotationRegistryTest {
    @Test
    public void should_register_annotations_in_specific_package() throws ClassNotFoundException {
        AnnotationRegistry annotationRegistry = new AnnotationRegistry("com.thoughtworks.maomao.stub.annotations");
        List<Class<? extends Annotation>> registeredAnnotations
                = annotationRegistry.getRegisteredAnnotations();
        assertThat(registeredAnnotations.size(), is(2));
        assertTrue(registeredAnnotations.contains(StubAnnotation.class));
        assertTrue(registeredAnnotations.contains(AnotherWheel.class));
    }

    @Test
    public void should_register_zero_annotations_in_specific_package() throws ClassNotFoundException {
        AnnotationRegistry annotationRegistry = new AnnotationRegistry("com.thoughtworks.maomao.stub.base.sub");
        List<Class<? extends Annotation>> registeredAnnotations
                = annotationRegistry.getRegisteredAnnotations();
        assertThat(registeredAnnotations.size(), is(0));
    }
}
