package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.Stub1;
import com.thoughtworks.maomao.stub.StubInterface;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WheelContainerTypeMappingTest {
    @Test
    public void should_find_class_by_class() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Class implementationClass = container.findImplementation(Stub1.class);
        assertThat(implementationClass, equalTo(Stub1.class));
    }

    @Test
    public void should_find_class_by_interface() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Class implementationClass = container.findImplementation(StubInterface.class);
        assertThat(implementationClass, equalTo(Stub1.class));
    }
}
