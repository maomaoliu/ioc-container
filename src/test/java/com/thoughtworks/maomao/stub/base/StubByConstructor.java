package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.others.OtherStub;

@Wheel
public class StubByConstructor {

    private Stub stub;
    private OtherStub otherStub;

    @Glue
    public StubByConstructor(Stub stub, OtherStub otherStub) {
        this.stub = stub;
        this.otherStub = otherStub;
    }

    public Stub getStub() {
        return stub;
    }

    public OtherStub getOtherStub() {
        return otherStub;
    }
}
