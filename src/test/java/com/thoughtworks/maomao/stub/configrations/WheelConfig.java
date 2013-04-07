package com.thoughtworks.maomao.stub.configrations;

import com.thoughtworks.maomao.annotations.Bean;
import com.thoughtworks.maomao.annotations.Configuration;
import com.thoughtworks.maomao.annotations.Wheel;

@Configuration
@Wheel
public class WheelConfig {

    public static final String WOODEN = "wooden";

    @Bean
    public Door door() {
        return new Door(WOODEN);
    }

}
