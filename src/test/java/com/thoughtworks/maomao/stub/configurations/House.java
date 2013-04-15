package com.thoughtworks.maomao.stub.configurations;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class House {

    private String type;
    private Door door;

    public House(String type) {

        this.type = type;
    }

    @Glue
    public House(Door door) {
        this.door = door;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Door getDoor() {
        return door;
    }
}
