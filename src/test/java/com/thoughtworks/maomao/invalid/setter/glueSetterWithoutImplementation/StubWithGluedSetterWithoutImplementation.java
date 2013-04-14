package com.thoughtworks.maomao.invalid.setter.glueSetterWithoutImplementation;

import com.thoughtworks.maomao.invalid.UnknownStub;
import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithGluedSetterWithoutImplementation {
    private UnknownStub unknownStub;

    public UnknownStub getUnknownStub() {
        return unknownStub;
    }

    @Glue
    public void setUnknownStub(UnknownStub unknownStub) {
        this.unknownStub = unknownStub;
    }
}
