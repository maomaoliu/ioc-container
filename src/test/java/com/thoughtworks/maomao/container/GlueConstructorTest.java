package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.base.Stub1;
import com.thoughtworks.maomao.stub.base.StubByConstructor;
import com.thoughtworks.maomao.stub.base.StubByConstructorWithMultipleParameters;
import com.thoughtworks.maomao.stub.base.invalid.StubWithUnknownClassInConstructor;
import com.thoughtworks.maomao.stub.base.invalid.StubWithoutDefaultConstructor;
import com.thoughtworks.maomao.stub.base.invalid.StubWithoutTwoGlueConstructors;
import com.thoughtworks.maomao.stub.base.sub.SubStub1;
import org.junit.Before;
import org.junit.Test;

import static com.thoughtworks.maomao.stub.base.invalid.StubWithoutPublicConstructor.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GlueConstructorTest {

    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub");
    }

    @Test
    public void should_glue_by_default_constructor() {
        Stub1 stub = container.getWheel(Stub1.class);
        assertNotNull(stub);
    }

    @Test
    public void should_glue_by_constructor() {
        StubByConstructor stub = container.getWheel(StubByConstructor.class);
        assertNotNull(stub);
        Stub1 stub1 = stub.getStub1();
        assertNotNull(stub1);
    }

    @Test
    public void should_glue_by_constructor_for_inner_static_class() {
        Stub1.InnerStaticStub1 stub = container.getWheel(Stub1.InnerStaticStub1.class);
        assertNotNull(stub);
        Stub1 stub1 = stub.getStub1();
        assertNotNull(stub1);
    }

    @Test
    public void should_glue_by_constructor_with_multiple_parameters() {
        StubByConstructorWithMultipleParameters stub = container.getWheel(StubByConstructorWithMultipleParameters.class);
        assertNotNull(stub);
        Stub1 stub1 = stub.getStub1();
        SubStub1 subStub1 = stub.getSubStub1();
        assertNotNull(stub1);
        assertNotNull(subStub1);
    }

    @Test
    public void should_throw_exception_if_constructor_is_not_public() {
        Class[] invalidClasses = new Class[]{PrivateGlueConstructor.class,
                                      ProtectedGlueConstructor.class,
                                      DefaultGlueConstructor.class,
                                      PrivateDefaultConstructor.class,
                                      ProtectedDefaultConstructor.class,
                                      DefaultDefaultConstructor.class};
        for (Class invalidClass : invalidClasses) {
            try {
                container.getWheel(invalidClass);
            } catch (InvalidWheelException e) {
                continue;
            }
            fail();
        }
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_more_than_one_glue_constructors() {
        container.getWheel(StubWithoutTwoGlueConstructors.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_without_glue() {
        container.getWheel(StubWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_with_unknown_class() {
        container.getWheel(StubWithUnknownClassInConstructor.class);
    }
}
