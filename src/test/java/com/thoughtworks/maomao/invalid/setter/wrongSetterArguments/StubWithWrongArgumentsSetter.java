package com.thoughtworks.maomao.invalid.setter.wrongSetterArguments;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.stub.base.sub.SubStub;

@Wheel
public class StubWithWrongArgumentsSetter {

    private Stub stub;
    private SubStub subStub;

    public Stub getStub() {
        return stub;
    }

    @Glue
    public void setStub(Stub stub, SubStub subStub) {
        this.stub = stub;
        this.subStub = subStub;
    }
}
