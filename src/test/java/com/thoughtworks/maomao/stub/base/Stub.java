package com.thoughtworks.maomao.stub.base;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;

@Wheel
public class Stub extends AbstractStub implements StubInterface {

    @Wheel
    public static class InnerStaticStub {
        private Stub stub;

        @Glue
        public InnerStaticStub(Stub stub) {
            this.stub = stub;
        }

        public Stub getStub() {
            return stub;
        }
    }
}
