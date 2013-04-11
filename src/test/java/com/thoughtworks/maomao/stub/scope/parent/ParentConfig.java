package com.thoughtworks.maomao.stub.scope.parent;

import com.thoughtworks.maomao.unit.annotations.Bean;
import com.thoughtworks.maomao.unit.annotations.Configuration;

@Configuration
public class ParentConfig {

    @Bean
    public Parent parent() {
        Parent parent = new Parent();
        parent.setName("maomao");
        return parent;
    }
}
