package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

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
