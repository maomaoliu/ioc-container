package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.Stub1;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WheelContainerTest {
    @Test
    public void should_load_wheels_in_specific_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        List<Class> wheels = container.getWheels();
        assertThat(wheels.size(), equalTo(1));
        assertThat(wheels.get(0), equalTo(Stub1.class));
    }

    @Test
    public void should_load_nothing_if_there_is_no_class_or_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.container");
        List<Class> wheels = container.getWheels();
        assertThat(wheels.size(), equalTo(0));
    }
}
