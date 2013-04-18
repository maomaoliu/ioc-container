package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.others.OtherStub;

@Wheel
public class StubWithSetter {

    private Stub stub;

    @Glue
    private OtherStub otherStub;

    public Stub getStub() {
        return stub;
    }

    @Glue
    public void setStub(Stub stub) {
        this.stub = stub;
    }

    public OtherStub getOtherStub() {
        return otherStub;
    }

    public void setOtherStub(OtherStub otherStub) {
        this.otherStub = otherStub;
    }
}
