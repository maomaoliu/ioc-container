package com.thoughtworks.maomao.stub.scope.child;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.scope.Person;

import java.util.Random;

@Wheel
public class Child implements Person {
    @Override
    public int age() {
        return new Random().nextInt(10);
    }
}
