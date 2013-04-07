package com.thoughtworks.maomao.stub.configrations;

import com.thoughtworks.maomao.annotations.Bean;
import com.thoughtworks.maomao.annotations.Configuration;

@Configuration
public class Config {

    public static final String TYPE = "apartment";

    @Bean
    public House house() {
        return new House(TYPE);
    }

}
