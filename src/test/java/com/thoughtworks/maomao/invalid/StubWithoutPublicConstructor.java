package com.thoughtworks.maomao.invalid;

import com.thoughtworks.maomao.unit.annotations.Glue;
import com.thoughtworks.maomao.unit.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

public class StubWithoutPublicConstructor {

    @Wheel
    public class InnerStub {

    }

    @Wheel
    public static class PrivateGlueConstructor{
        private Stub stub;

        @Glue
        private PrivateGlueConstructor(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class ProtectedGlueConstructor{
        private Stub stub;

        @Glue
        protected ProtectedGlueConstructor(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class DefaultGlueConstructor{
        private Stub stub;

        @Glue
        DefaultGlueConstructor(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class PrivateDefaultConstructor{
        private PrivateDefaultConstructor() {
        }
    }

    @Wheel
    public static class ProtectedDefaultConstructor{
        protected ProtectedDefaultConstructor() {
        }
    }

    @Wheel
    public static class DefaultDefaultConstructor{
        DefaultDefaultConstructor() {
        }
    }
}
