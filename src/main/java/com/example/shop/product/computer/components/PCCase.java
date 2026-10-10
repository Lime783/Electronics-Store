package com.example.shop.product.computer.components;

import com.example.exceptions.NegativeValueException;

public record PCCase(int width, int height) {
    public PCCase {
        if (width <= 0 || height <= 0) {
            throw new NegativeValueException("width and height must be positive: " + width + " x " + height);
        }
    }
}
