package com.example.exceptions;

public class NotEnoughProductsException extends RuntimeException {
    public NotEnoughProductsException(String message) {
        super(message);
    }
}
