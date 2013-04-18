package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.StubWithGluedField;
import com.thoughtworks.maomao.stub.StubWithGluedFieldAndSetter;
import com.thoughtworks.maomao.stub.annotations.AnotherWheel;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.StubWithSetter;
import com.thoughtworks.maomao.stub.base.sub.SubStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GlueSetterTest {

    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub", new Class[]{Wheel.class, AnotherWheel.class});
    }

    @Test
    public void should_glue_by_setter() throws InvalidWheelException {
        StubWithSetter wheel = container.getWheelInstance(StubWithSetter.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
        assertNotNull(wheel.getOtherStub());
    }

    @Test
    public void should_glue_by_field() throws InvalidWheelException {
        StubWithGluedField wheel = container.getWheelInstance(StubWithGluedField.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
    }

    @Test
    public void should_glue_by_field_and_setter() throws InvalidWheelException {
        StubWithGluedFieldAndSetter wheel = container.getWheelInstance(StubWithGluedFieldAndSetter.class);
        assertNotNull(wheel);
        assertEquals(Stub.class, wheel.getStub().getClass());
        assertEquals(SubStub.class, wheel.getSubStub().getClass());
    }
}
