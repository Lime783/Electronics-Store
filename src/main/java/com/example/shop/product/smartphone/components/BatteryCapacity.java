package com.example.shop.product.smartphone.components;

import lombok.Getter;

@Getter
public enum BatteryCapacity {
    CAPACITY_1000MAH("1000"),
    CAPACITY_2000MAH("2000"),
    CAPACITY_3000MAH("3000");

    private final String capacity;

    BatteryCapacity(String capacity) {
        this.capacity = capacity;
    }
}
