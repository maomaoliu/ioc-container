package com.thoughtworks.maomao.exception;

public class InvalidWheelException extends Exception {
    public InvalidWheelException(Exception e) {
        super(e);
    }

    public InvalidWheelException(String message) {
        super(message);
    }
}
