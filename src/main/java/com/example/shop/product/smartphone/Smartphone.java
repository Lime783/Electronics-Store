package com.example.shop.product.smartphone;

import com.example.shop.product.Product;
import com.example.shop.product.smartphone.components.Accessory;
import com.example.shop.product.smartphone.components.BatteryCapacity;
import com.example.shop.product.smartphone.components.Color;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
