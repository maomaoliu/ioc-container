package com.thoughtworks.maomao.stub.base.invalid;

import com.thoughtworks.maomao.annotations.Glue;
import com.thoughtworks.maomao.annotations.Wheel;
import com.thoughtworks.maomao.stub.base.Stub;

public class StubWithInvalidSetter {

    @Wheel
    public static class StubWithGluedNonPublicSetter {
        private Stub stub;

        public Stub getStub() {
            return stub;
        }

        @Glue
        protected void setStub(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class StubWithGluedFieldAndNonPublicSetter {
        @Glue
        private Stub stub;

        public Stub getStub() {
            return stub;
        }

        protected void setStub(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class StubWithGluedFieldWithoutDefaultConstructor {
        @Glue
        private Stub stub;

        public StubWithGluedFieldWithoutDefaultConstructor(Stub stub) {
            this.stub = stub;
        }

        public Stub getStub() {
            return stub;
        }

        public void setStub(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class StubWithGluedSetterWithoutDefaultConstructor {
        private Stub stub;

        public StubWithGluedSetterWithoutDefaultConstructor(Stub stub) {
            this.stub = stub;
        }

        public Stub getStub() {
            return stub;
        }

        @Glue
        public void setStub(Stub stub) {
            this.stub = stub;
        }
    }

    @Wheel
    public static class StubWithGluedSetterWithoutField {
        @Glue
        public void setStub1(Stub stub) {
            System.out.println("----------------- stub = " + stub);
        }
    }

    @Wheel
    public static class StubWithGluedFieldWithoutSetter {
        @Glue
        private Stub stub;
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
