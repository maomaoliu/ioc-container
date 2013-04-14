package com.thoughtworks.maomao.invalid;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class InvalidSelfCircularStub {
    private InvalidSelfCircularStub stub;

    @Glue
    public InvalidSelfCircularStub(InvalidSelfCircularStub stub) {
        this.stub = stub;
    }
}
