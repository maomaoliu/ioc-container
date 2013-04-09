package com.thoughtworks.maomao.stub.scope.parent;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.scope.Person;

import java.util.Random;

@Wheel
public class Parent implements Person {
    @Override
    public int age() {
        return new Random().nextInt(20) + 20;
    }
}
