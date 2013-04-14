package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.StubByConstructor;
import com.thoughtworks.maomao.stub.base.StubByConstructorWithMultipleParameters;
import com.thoughtworks.maomao.invalid.StubWithUnknownClassInConstructor;
import com.thoughtworks.maomao.invalid.StubWithoutDefaultConstructor;
import com.thoughtworks.maomao.invalid.StubWithoutTwoGlueConstructors;
import com.thoughtworks.maomao.stub.base.sub.SubStub;
import org.junit.Before;
import org.junit.Test;

import static com.thoughtworks.maomao.invalid.StubWithoutPublicConstructor.*;
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
        Stub stub = container.getWheelInstance(Stub.class);
        assertNotNull(stub);
    }

    @Test
    public void should_glue_by_constructor() {
        StubByConstructor stub = container.getWheelInstance(StubByConstructor.class);
        assertNotNull(stub);
        Stub stub1 = stub.getStub();
        assertNotNull(stub1);
    }

    @Test
    public void should_glue_by_constructor_for_inner_static_class() {
        Stub.InnerStaticStub stub = container.getWheelInstance(Stub.InnerStaticStub.class);
        assertNotNull(stub);
        Stub stub1 = stub.getStub();
        assertNotNull(stub1);
    }

    @Test
    public void should_glue_by_constructor_with_multiple_parameters() {
        StubByConstructorWithMultipleParameters stub = container.getWheelInstance(StubByConstructorWithMultipleParameters.class);
        assertNotNull(stub);
        Stub stub1 = stub.getStub();
        SubStub subStub = stub.getSubStub();
        assertNotNull(stub1);
        assertNotNull(subStub);
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
                container.getWheelInstance(invalidClass);
            } catch (InvalidWheelException e) {
                continue;
            }
            fail();
        }
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_more_than_one_glue_constructors() {
        container.getWheelInstance(StubWithoutTwoGlueConstructors.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_without_glue() {
        container.getWheelInstance(StubWithoutDefaultConstructor.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_constructor_with_unknown_class() {
        container.getWheelInstance(StubWithUnknownClassInConstructor.class);
    }
}
