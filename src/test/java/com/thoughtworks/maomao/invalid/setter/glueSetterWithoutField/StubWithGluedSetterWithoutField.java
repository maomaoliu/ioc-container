package com.thoughtworks.maomao.invalid.setter.glueSetterWithoutField;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class StubWithGluedSetterWithoutField {
    @Glue
    public void setStub1(Stub stub) {
        System.out.println("----------------- stub = " + stub);
    }
}
