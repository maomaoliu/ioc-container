package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.Stub1;
import com.thoughtworks.maomao.stub.sub.SubStub1;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WheelContainerTest {
    @Test
    public void should_load_wheels_in_specific_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub.sub");
        Collection<Class> wheels = container.getWheels();
        assertThat(wheels.size(), equalTo(1));
        assertThat((Class)wheels.toArray()[0], equalTo(SubStub1.class));
    }

    @Test
    public void should_also_load_wheels_in_specific_package_and_sub_packages() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.stub");
        Collection<Class> wheels = container.getWheels();
        assertThat(wheels.size(), equalTo(2));
        assertThat((Class)wheels.toArray()[0], equalTo(Stub1.class));
        assertThat((Class)wheels.toArray()[1], equalTo(SubStub1.class));
    }

    @Test
    public void should_load_nothing_if_there_is_no_class_or_package() {
        WheelContainer container = new WheelContainer("com.thoughtworks.maomao.container");
        Collection<Class> wheels = container.getWheels();
        assertThat(wheels.size(), equalTo(0));
    }
}
