package com.example.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public abstract class Product {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private int amountAvailable;

    public Product(String name, BigDecimal price, int amountAvailable) {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(price, "price cannot be null");

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }
}
