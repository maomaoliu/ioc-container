package com.thoughtworks.maomao.unit.annotations;

import com.thoughtworks.maomao.stub.base.Stub;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WheelTest {

    @Test
    public void should_get_annotation() {
        Annotation[] annotations = Stub.class.getDeclaredAnnotations();
        assertThat(annotations.length, equalTo(1));
        assertThat((Class<Wheel>) annotations[0].annotationType(), equalTo(Wheel.class));
    }
}
