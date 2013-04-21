package com.thoughtworks.maomao.invalid.setter.glueFieldWithoutImplementation;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.invalid.UnknownStub;

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
