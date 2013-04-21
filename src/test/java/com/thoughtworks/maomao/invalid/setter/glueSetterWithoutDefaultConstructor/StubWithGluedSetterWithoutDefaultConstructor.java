package com.thoughtworks.maomao.invalid.setter.glueSetterWithoutDefaultConstructor;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

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
