package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub1;

public class StubWithoutPublicConstructor {

    @Wheel
    public class InnerStub {

    }

    @Wheel
    public static class PrivateGlueConstructor{
        private Stub1 stub1;

        @Glue
        private PrivateGlueConstructor(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class ProtectedGlueConstructor{
        private Stub1 stub1;

        @Glue
        protected ProtectedGlueConstructor(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class DefaultGlueConstructor{
        private Stub1 stub1;

        @Glue
        DefaultGlueConstructor(Stub1 stub1) {
            this.stub1 = stub1;
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
