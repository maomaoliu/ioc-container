package com.thoughtworks.maomao.stub.configurations;

import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class House {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public House(String type) {

        this.type = type;
    }
}
