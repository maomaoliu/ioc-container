package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithSetter {

    private Stub stub;

    public Stub getStub() {
        return stub;
    }

    @Glue
    public void setStub(Stub stub) {
        this.stub = stub;
    }
}
