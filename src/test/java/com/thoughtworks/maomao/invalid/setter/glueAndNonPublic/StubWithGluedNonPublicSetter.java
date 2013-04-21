package com.thoughtworks.maomao.invalid.setter.glueAndNonPublic;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

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
