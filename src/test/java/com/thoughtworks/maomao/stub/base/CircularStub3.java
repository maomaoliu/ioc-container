package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class CircularStub3 {

    @Glue
    private CircularStub1 stub1;

    private CircularStub2 stub2;

    public CircularStub1 getStub1() {
        return stub1;
    }

    public void setStub1(CircularStub1 stub1) {
        this.stub1 = stub1;
    }

    public CircularStub2 getStub2() {
        return stub2;
    }

    @Glue
    public void setStub2(CircularStub2 stub2) {
        this.stub2 = stub2;
    }
}
