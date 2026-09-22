package com.example.product.smartphone.components;

import lombok.Getter;

@Getter
public enum Accessory {
    CASE("Case"),
    SCREEN_PROTECTION("Screen protection");

    private final String accessory;

    Accessory(String accessory) {
        this.accessory = accessory;
    }
}
