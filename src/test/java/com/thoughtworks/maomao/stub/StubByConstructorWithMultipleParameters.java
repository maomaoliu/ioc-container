package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.sub.SubStub1;

@Wheel
public class StubByConstructorWithMultipleParameters {
    private Stub1 stub1;
    private SubStub1 subStub1;

    @Glue
    public StubByConstructorWithMultipleParameters(Stub1 stub1, SubStub1 subStub1) {
        this.stub1 = stub1;
        this.subStub1 = subStub1;
    }

    public Stub1 getStub1() {
        return stub1;
    }

    public SubStub1 getSubStub1() {
        return subStub1;
    }
}
