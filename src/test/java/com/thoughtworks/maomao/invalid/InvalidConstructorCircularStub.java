package com.thoughtworks.maomao.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;

public class InvalidConstructorCircularStub {
    @Wheel
    public static class StubA {
        @Glue
        public StubA(StubB stubB) {
        }
    }

    @Wheel
    public static class StubB{
        @Glue
        public StubB(StubA stubA) {
        }
    }
}
