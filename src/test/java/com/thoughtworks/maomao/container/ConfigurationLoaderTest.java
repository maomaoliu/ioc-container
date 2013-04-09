package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.stub.configurations.Door;
import com.thoughtworks.maomao.stub.configurations.WheelConfig;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConfigurationLoaderTest {

    @Test
    public void should_load_beans_by_configuration() {
        List<Class> configClasses = Arrays.asList(new Class[] {WheelConfig.class});
        ConfigurationLoader configurationLoader = new ConfigurationLoader(configClasses);
        Map<Class, List> beans = configurationLoader.getBeans();
        assertThat(beans.size(), is(1));
        List doors = beans.get(Door.class);
        assertThat(doors.size(), is(1));
        assertThat(((Door)doors.get(0)).getMaterial(), is(WheelConfig.WOODEN));
    }
}
