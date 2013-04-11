package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

@Wheel
public class StubWithGluedField {

    @Glue
    private Stub stub;

    public Stub getStub() {
        return stub;
    }

    public void setStub(Stub stub) {
        this.stub = stub;
    }
}
