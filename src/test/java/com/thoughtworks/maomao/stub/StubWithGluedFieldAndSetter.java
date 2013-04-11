package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.sub.SubStub;

@Wheel
public class StubWithGluedFieldAndSetter {
    @Glue
    private Stub stub;

    private SubStub subStub;

    public Stub getStub() {
        return stub;
    }

    public void setStub(Stub stub) {
        this.stub = stub;
    }

    public SubStub getSubStub() {
        return subStub;
    }

    @Glue
    public void setSubStub(SubStub subStub) {
        this.subStub = subStub;
    }
}
