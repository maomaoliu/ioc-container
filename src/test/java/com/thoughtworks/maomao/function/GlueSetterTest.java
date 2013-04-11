package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.StubWithGluedField;
import com.thoughtworks.maomao.stub.StubWithGluedFieldAndSetter;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.StubWithSetter;
import com.thoughtworks.maomao.stub.base.sub.SubStub;
import org.junit.Before;
import org.junit.Test;

import static com.thoughtworks.maomao.stub.base.invalid.StubWithInvalidSetter.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GlueSetterTest {

    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_glue_by_setter() throws InvalidWheelException {
        StubWithSetter wheel = container.getWheel(StubWithSetter.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
    }

    @Test
    public void should_glue_by_field() throws InvalidWheelException {
        StubWithGluedField wheel = container.getWheel(StubWithGluedField.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
    }

    @Test
    public void should_glue_by_field_and_setter() throws InvalidWheelException {
        StubWithGluedFieldAndSetter wheel = container.getWheel(StubWithGluedFieldAndSetter.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
        assertEquals(SubStub.class, wheel.getSubStub().getClass());
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_setter_is_not_public() throws InvalidWheelException {
        container.getWheel(StubWithGluedNonPublicSetter.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_setter_is_not_public() throws InvalidWheelException {
        container.getWheel(StubWithGluedFieldAndNonPublicSetter.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_wheel_could_not_be_found() throws InvalidWheelException {
        container.getWheel(StubWithGluedFieldWithoutImplementation.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_wheel_could_not_be_found() throws InvalidWheelException {
        container.getWheel(StubWithGluedSetterWithoutImplementation.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_setter_could_not_be_found() throws InvalidWheelException {
        container.getWheel(StubWithGluedSetterWithoutField.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_field_could_not_be_found() throws InvalidWheelException {
        container.getWheel(StubWithGluedFieldWithoutSetter.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_setter_and_has_no_default_constructor() throws InvalidWheelException {
        container.getWheel(StubWithGluedSetterWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_when_glue_by_field_and_has_no_default_constructor() throws InvalidWheelException {
        container.getWheel(StubWithGluedFieldWithoutDefaultConstructor.class);
    }
}
