package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.invalid.setter.glueFieldWithoutDefaultConstructor.StubWithGluedFieldWithoutDefaultConstructor;
import com.thoughtworks.maomao.invalid.setter.glueFieldWithoutImplementation.StubWithGluedFieldWithoutImplementation;
import com.thoughtworks.maomao.invalid.setter.glueSetterWithoutDefaultConstructor.StubWithGluedSetterWithoutDefaultConstructor;
import com.thoughtworks.maomao.invalid.setter.glueSetterWithoutImplementation.StubWithGluedSetterWithoutImplementation;
import org.junit.Test;

public class InvalidGlueSetterTest {
    private WheelContainer container;

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_setter_is_not_public() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueAndNonPublic", new Class[]{Wheel.class});
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_setter_is_not_public() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueFieldAndNonPublicSetter", new Class[]{Wheel.class});
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_wheel_could_not_be_found() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueFieldWithoutImplementation", new Class[]{Wheel.class});
        container.getWheelInstance(StubWithGluedFieldWithoutImplementation.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_wheel_could_not_be_found() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueSetterWithoutImplementation", new Class[]{Wheel.class});
        container.getWheelInstance(StubWithGluedSetterWithoutImplementation.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_field_could_not_be_found() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueFieldWithoutSetter", new Class[]{Wheel.class});
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_setter_could_not_be_found() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueFieldWithoutSetter", new Class[]{Wheel.class});
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_has_no_default_constructor() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueSetterWithoutDefaultConstructor", new Class[]{Wheel.class});
        container.getWheelInstance(StubWithGluedSetterWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_has_no_default_constructor() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.glueFieldWithoutDefaultConstructor", new Class[]{Wheel.class});
        container.getWheelInstance(StubWithGluedFieldWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_setter_has_wrong_number_arguments() throws InvalidWheelException {
        container = new WheelContainer("com.thoughtworks.maomao.invalid.setter.wrongSetterArguments", new Class[]{Wheel.class});
    }
}
