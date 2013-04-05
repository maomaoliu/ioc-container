package com.thoughtworks.maomao.stub.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.Stub1;

@Wheel
public class StubWithoutTwoGlueConstructors {
    private Stub1 stub1;

    @Glue
    public StubWithoutTwoGlueConstructors() {
    }

    @Glue
    public StubWithoutTwoGlueConstructors(Stub1 stub1) {
        this.stub1 = stub1;
    }
}
