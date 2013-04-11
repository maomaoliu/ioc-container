package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;
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
