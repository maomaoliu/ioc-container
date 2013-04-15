package com.thoughtworks.maomao.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

@Wheel
public class StubWithoutTwoGlueConstructors {
    private Stub stub;

    @Glue
    public StubWithoutTwoGlueConstructors() {
    }

    @Glue
    public StubWithoutTwoGlueConstructors(Stub stub) {
        this.stub = stub;
    }
}
