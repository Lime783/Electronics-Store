package com.example.exceptions;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String message) {
        super(message);
    }
}
