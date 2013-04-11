package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.unit.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

@Wheel
public class StubWithoutDefaultConstructor {
    private Stub stub;

    public StubWithoutDefaultConstructor(Stub stub) {
        this.stub = stub;
    }
}
