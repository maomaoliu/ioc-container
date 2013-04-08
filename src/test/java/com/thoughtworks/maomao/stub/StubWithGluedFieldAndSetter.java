package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub1;
import com.thoughtworks.maomao.stub.base.sub.SubStub1;

@Wheel
public class StubWithGluedFieldAndSetter {
    @Glue
    private Stub1 stub1;

    private SubStub1 subStub1;

    public Stub1 getStub1() {
        return stub1;
    }

    public void setStub1(Stub1 stub1) {
        this.stub1 = stub1;
    }

    public SubStub1 getSubStub1() {
        return subStub1;
    }

    @Glue
    public void setSubStub1(SubStub1 subStub1) {
        this.subStub1 = subStub1;
    }
}
