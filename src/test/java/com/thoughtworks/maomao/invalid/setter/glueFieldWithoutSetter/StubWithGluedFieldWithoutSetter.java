package com.thoughtworks.maomao.invalid.setter.glueFieldWithoutSetter;

import com.thoughtworks.maomao.stub.base.Stub;
import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

@Wheel
public class StubWithGluedFieldWithoutSetter {
    @Glue
    private Stub stub;
}
