package com.example.product.smartphone.components;

import lombok.Getter;

@Getter
public enum Color {
    BLACK("black"),
    WHITE("white"),
    RED("red");

    private final String color;

    Color(String color) {
        this.color = color;
    }
}
