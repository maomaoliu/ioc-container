package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class CircularStub1 {
    private CircularStub2 stub2;
    private CircularStub3 stub3;

    @Glue
    public CircularStub1(CircularStub2 stub2, CircularStub3 stub3) {
        this.stub2 = stub2;
        this.stub3 = stub3;
    }

    public CircularStub2 getStub2() {
        return stub2;
    }

    public CircularStub3 getStub3() {
        return stub3;
    }
}
