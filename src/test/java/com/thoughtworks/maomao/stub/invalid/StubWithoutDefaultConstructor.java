package com.thoughtworks.maomao.stub.invalid;

import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.Stub1;

@Wheel
public class StubWithoutDefaultConstructor {
    private Stub1 stub1;

    public StubWithoutDefaultConstructor(Stub1 stub1) {
        this.stub1 = stub1;
    }
}
