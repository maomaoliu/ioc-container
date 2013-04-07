package com.thoughtworks.maomao.stub.configrations;

import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class Window {
    private String material;

    public Window(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
