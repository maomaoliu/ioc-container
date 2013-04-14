package com.thoughtworks.maomao.invalid.setter.glueAndNonPublic;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithGluedNonPublicSetter {
    private Stub stub;

    public Stub getStub() {
        return stub;
    }

    @Glue
    protected void setStub(Stub stub) {
        this.stub = stub;
    }
}
