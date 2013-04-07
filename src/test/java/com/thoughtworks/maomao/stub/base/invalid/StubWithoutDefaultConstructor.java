package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub1;

@Wheel
public class StubWithoutDefaultConstructor {
    private Stub1 stub1;

    public StubWithoutDefaultConstructor(Stub1 stub1) {
        this.stub1 = stub1;
    }
}
