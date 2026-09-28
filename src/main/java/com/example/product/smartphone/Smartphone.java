package com.example.product.smartphone;

import com.example.product.Product;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Smartphone extends Product {
    Accessory accessory;
    BatteryCapacity batteryCapacity;
    Color color;

    public Smartphone(String name, BigDecimal price, int amountAvailable, Accessory accessory, BatteryCapacity batteryCapacity, Color color) {
        super(name, price, amountAvailable);

        Objects.requireNonNull(name, "name cannot null");
        Objects.requireNonNull(price, "price cannot null");

        this.accessory = accessory;
        this.batteryCapacity = batteryCapacity;
        this.color = color;
    }
}
