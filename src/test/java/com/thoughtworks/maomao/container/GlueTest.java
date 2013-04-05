package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.Stub1;
import com.thoughtworks.maomao.stub.StubByConstructor;
import com.thoughtworks.maomao.stub.StubByConstructorWithMultipleParameters;
import com.thoughtworks.maomao.stub.invalid.StubWithUnknownClassInConstructor;
import com.thoughtworks.maomao.stub.invalid.StubWithoutDefaultConstructor;
import com.thoughtworks.maomao.stub.sub.SubStub1;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GlueTest {

    private WheelContainer container;

    @Before
    public void setUp() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_glue_by_default_constructor() throws InvalidWheelException {
        Stub1 stub = container.getWheel(Stub1.class);
        assertNotNull(stub);
    }

    @Test
    public void should_glue_by_constructor() throws InvalidWheelException {
        StubByConstructor stub = container.getWheel(StubByConstructor.class);
        assertNotNull(stub);
        Stub1 stub1 = stub.getStub1();
        assertNotNull(stub1);
    }

    @Test
    public void should_glue_by_constructor_with_multiple_parameters() throws InvalidWheelException {
        StubByConstructorWithMultipleParameters stub = container.getWheel(StubByConstructorWithMultipleParameters.class);
        assertNotNull(stub);
        Stub1 stub1 = stub.getStub1();
        SubStub1 subStub1 = stub.getSubStub1();
        assertNotNull(stub1);
        assertNotNull(subStub1);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_without_glue() throws InvalidWheelException {
        container.getWheel(StubWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_with_unknown_class() throws InvalidWheelException {
        container.getWheel(StubWithUnknownClassInConstructor.class);
    }
}
