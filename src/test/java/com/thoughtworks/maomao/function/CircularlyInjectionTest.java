package com.thoughtworks.maomao.function;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.container.WheelContainer;
import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.base.CircularStub1;
import com.thoughtworks.maomao.stub.base.CircularStub2;
import com.thoughtworks.maomao.stub.base.CircularStub3;
import com.thoughtworks.maomao.invalid.InvalidSelfCircularStub;
import org.junit.Before;
import org.junit.Test;

import static com.thoughtworks.maomao.invalid.InvalidConstructorCircularStub.StubA;
import static org.junit.Assert.assertEquals;

public class CircularlyInjectionTest {

    private WheelContainer container;

    @Before
    public void setup() throws Exception {
        container = new WheelContainer("com.thoughtworks.maomao.stub", new Class[]{Wheel.class});
    }

    @Test
    public void should_glue_correctly_for_circularly_injection() {
        CircularStub3 stub3 = container.getWheelInstance(CircularStub3.class);
        CircularStub1 stub1InStub3 = stub3.getStub1();
        CircularStub2 stub2 = stub3.getStub2();
        CircularStub1 stub1InStub2 = stub2.getStub1();
        assertEquals(stub1InStub3, stub1InStub2);
        assertEquals(stub3, stub1InStub2.getStub3() );
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_there_is_self_circularly_injection() {
        container.getWheelInstance(InvalidSelfCircularStub.class);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_throw_exception_if_there_is_constructor_circularly_injection() {
        container.getWheelInstance(StubA.class);
    }
}
