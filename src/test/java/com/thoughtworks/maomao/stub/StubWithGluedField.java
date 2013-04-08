package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub1;

@Wheel
public class StubWithGluedField {

    @Glue
    private Stub1 stub1;

    public Stub1 getStub1() {
        return stub1;
    }

    public void setStub1(Stub1 stub1) {
        this.stub1 = stub1;
    }
}
