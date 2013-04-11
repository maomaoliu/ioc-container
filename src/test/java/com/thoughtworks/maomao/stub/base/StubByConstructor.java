package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubByConstructor {

    private Stub stub;

    @Glue
    public StubByConstructor(Stub stub) {
        this.stub = stub;
    }

    public Stub getStub() {
        return stub;
    }
}
