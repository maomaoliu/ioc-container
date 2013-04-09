package com.thoughtworks.maomao.stub.configurations;

import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class Door {
   private String material;

    public Door(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
