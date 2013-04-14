package com.thoughtworks.maomao.invalid.setter.glueFieldWithoutImplementation;

import com.thoughtworks.maomao.invalid.UnknownStub;
import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithGluedFieldWithoutImplementation {
    @Glue
    private UnknownStub unknownStub;

    public UnknownStub getUnknownStub() {
        return unknownStub;
    }

    public void setUnknownStub(UnknownStub unknownStub) {
        this.unknownStub = unknownStub;
    }
}
