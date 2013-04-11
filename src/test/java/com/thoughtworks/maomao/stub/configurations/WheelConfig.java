package com.thoughtworks.maomao.stub.configurations;

import com.thoughtworks.maomao.unit.annotations.Bean;
import com.thoughtworks.maomao.unit.annotations.Configuration;

@Configuration
public class WheelConfig {

    public static final String WOODEN = "wooden";

    @Bean
    public Door door() {
        return new Door(WOODEN);
    }

    @Bean
    protected Bed bed() {
        return new Bed("bad bed");
    }

}
