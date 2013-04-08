package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub1;

public class StubWithInvalidSetter {

    @Wheel
    public static class StubWithGluedNonPublicSetter {
        private Stub1 stub1;

        public Stub1 getStub1() {
            return stub1;
        }

        @Glue
        protected void setStub1(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class StubWithGluedFieldAndNonPublicSetter {
        @Glue
        private Stub1 stub1;

        public Stub1 getStub1() {
            return stub1;
        }

        protected void setStub1(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class StubWithGluedFieldWithoutDefaultConstructor {
        @Glue
        private Stub1 stub1;

        public StubWithGluedFieldWithoutDefaultConstructor(Stub1 stub1) {
            this.stub1 = stub1;
        }

        public Stub1 getStub1() {
            return stub1;
        }

        public void setStub1(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class StubWithGluedSetterWithoutDefaultConstructor {
        private Stub1 stub1;

        public StubWithGluedSetterWithoutDefaultConstructor(Stub1 stub1) {
            this.stub1 = stub1;
        }

        public Stub1 getStub1() {
            return stub1;
        }

        @Glue
        public void setStub1(Stub1 stub1) {
            this.stub1 = stub1;
        }
    }

    @Wheel
    public static class StubWithGluedSetterWithoutField {
        @Glue
        public void setStub1(Stub1 stub1) {
            System.out.println("----------------- stub1 = " + stub1);
        }
    }

    @Wheel
    public static class StubWithGluedFieldWithoutSetter {
        @Glue
        private Stub1 stub1;
    }

    @Wheel
    public static class StubWithGluedFieldWithoutImplementation {
        @Glue
        private UnknownStub unknownStub;

        public UnknownStub getUnknownStub() {
            return unknownStub;
        }

        public void setUnknownStub(UnknownStub unknownStub) {
            this.unknownStub = unknownStub;
        }
    }

    @Wheel
    public static class StubWithGluedSetterWithoutImplementation {
        private UnknownStub unknownStub;

        public UnknownStub getUnknownStub() {
            return unknownStub;
        }

        @Glue
        public void setUnknownStub(UnknownStub unknownStub) {
            this.unknownStub = unknownStub;
        }
    }
}
