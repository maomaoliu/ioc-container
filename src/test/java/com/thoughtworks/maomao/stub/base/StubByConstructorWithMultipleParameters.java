package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.sub.SubStub;

@Wheel
public class StubByConstructorWithMultipleParameters {
    private Stub stub;
    private SubStub subStub;

    @Glue
    public StubByConstructorWithMultipleParameters(Stub stub, SubStub subStub) {
        this.stub = stub;
        this.subStub = subStub;
    }

    public Stub getStub() {
        return stub;
    }

    public SubStub getSubStub() {
        return subStub;
    }
}
