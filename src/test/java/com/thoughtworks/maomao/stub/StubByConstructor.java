package com.thoughtworks.maomao.stub;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class StubByConstructor {

    private Stub1 stub1;

    @Glue
    public StubByConstructor(Stub1 stub1) {
        this.stub1 = stub1;
    }

    public Stub1 getStub1() {
        return stub1;
    }
}
