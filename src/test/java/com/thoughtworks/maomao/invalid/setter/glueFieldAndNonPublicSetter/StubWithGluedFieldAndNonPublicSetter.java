package com.thoughtworks.maomao.invalid.setter.glueFieldAndNonPublicSetter;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class StubWithGluedFieldAndNonPublicSetter {
    @Glue
    private Stub stub;

    public Stub getStub() {
        return stub;
    }

    protected void setStub(Stub stub) {
        this.stub = stub;
    }
}
