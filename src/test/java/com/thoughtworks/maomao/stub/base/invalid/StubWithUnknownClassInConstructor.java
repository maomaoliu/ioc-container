package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithUnknownClassInConstructor {
    private UnknownStub unknownStub;

    @Glue
    public StubWithUnknownClassInConstructor(UnknownStub unknownStub) {
        this.unknownStub = unknownStub;
    }
}
