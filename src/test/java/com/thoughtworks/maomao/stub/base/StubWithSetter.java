package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import sun.tools.jconsole.inspector.Utils;

@Wheel
public class StubWithSetter {

    private Stub1 stub1;

    public Stub1 getStub1() {
        return stub1;
    }

    @Glue
    public void setStub1(Stub1 stub1) {
        this.stub1 = stub1;
    }
}
