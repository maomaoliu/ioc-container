package com.thoughtworks.maomao.invalid.setter.glueFieldWithoutDefaultConstructor;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class StubWithGluedFieldWithoutDefaultConstructor {
    @Glue
    private Stub stub;

    public StubWithGluedFieldWithoutDefaultConstructor(Stub stub) {
        this.stub = stub;
    }

    public Stub getStub() {
        return stub;
    }

    public void setStub(Stub stub) {
        this.stub = stub;
    }
}
