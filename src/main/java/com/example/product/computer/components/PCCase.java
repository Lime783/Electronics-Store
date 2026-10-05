package com.example.product.computer.components;

public record PCCase(int width, int height) {
    public PCCase {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive: " + width + " x " + height);
        }
    }
}
