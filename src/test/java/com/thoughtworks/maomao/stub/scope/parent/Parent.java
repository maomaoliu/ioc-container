package com.thoughtworks.maomao.stub.scope.parent;

import com.thoughtworks.maomao.unit.annotations.Wheel;
import com.thoughtworks.maomao.stub.scope.Person;

import java.util.Random;

@Wheel
public class Parent implements Person {

    private String name;

    @Override
    public int age() {
        return new Random().nextInt(20) + 20;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
