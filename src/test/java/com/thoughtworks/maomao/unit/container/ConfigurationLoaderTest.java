package com.thoughtworks.maomao.unit.container;

import com.thoughtworks.maomao.stub.configurations.Bed;
import com.thoughtworks.maomao.stub.configurations.Door;
import com.thoughtworks.maomao.stub.configurations.WheelConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ConfigurationLoaderTest {

    private Loader loader;
    private ConfigurationLoader configurationLoader;

    @Before
    public void setup() throws Exception {
        loader = new Loader("com.thoughtworks.maomao.stub.configurations");
        configurationLoader = new ConfigurationLoader(loader);
    }

    @Test
    public void should_load_beans_by_configuration() {
        List doors = configurationLoader.getBeans(Door.class);
        assertThat(doors.size(), is(1));
        assertThat(((Door) doors.get(0)).getMaterial(), is(WheelConfig.WOODEN));
    }

    @Test
    public void should_not_load_non_public_beans() {
        List beds = configurationLoader.getBeans(Bed.class);
        assertEquals(0, beds.size());
    }
}
