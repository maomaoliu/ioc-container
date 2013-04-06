package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.annotations.StubAnnotation;
import org.junit.Test;

import java.lang.annotation.*;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class AnnotationRegistryTest {
    @Test
    public void shouldRegisterAnnotationsInSpecificPackage() throws ClassNotFoundException {
        AnnotationRegistry annotationRegistry = new AnnotationRegistry("com.thoughtworks.maomao.stub.annotations");
        List<Class<? extends Annotation>> registeredAnnotations
                = annotationRegistry.getRegisteredAnnotations();
        assertThat(registeredAnnotations.size(), is(1));
        Class<? extends Annotation> registeredAnnotation = registeredAnnotations.get(0);
        assertEquals(StubAnnotation.class, registeredAnnotation);
    }

    @Test
    public void shouldRegisterZeroAnnotationsInSpecificPackage() throws ClassNotFoundException {
        AnnotationRegistry annotationRegistry = new AnnotationRegistry("com.thoughtworks.maomao.stub.sub");
        List<Class<? extends Annotation>> registeredAnnotations
                = annotationRegistry.getRegisteredAnnotations();
        assertThat(registeredAnnotations.size(), is(0));
    }
}
