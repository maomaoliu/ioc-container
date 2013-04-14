package com.thoughtworks.maomao.invalid.setter.glueSetterWithoutDefaultConstructor;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithGluedSetterWithoutDefaultConstructor {
    private Stub stub;

    public StubWithGluedSetterWithoutDefaultConstructor(Stub stub) {
        this.stub = stub;
    }

    public Stub getStub() {
        return stub;
    }

    @Glue
    public void setStub(Stub stub) {
        this.stub = stub;
    }
}
