package com.example.product;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Product {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private int amountAvailable;

    public Product(String name, BigDecimal price, int amountAvailable) {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(price, "price cannot be null");

        validateData(name, price, amountAvailable);

        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    private void validateData(String name, BigDecimal price, int amountAvailable) {
        validateName(name);
        validatePrice(price);
        validateAmountAvailable(amountAvailable);
    }

    private void validateName(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("name cannot be less than 3 characters: " + name);
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be negative: " + price);
        }
    }

    private void validateAmountAvailable(int amountAvailable) {
        if (amountAvailable <= 0) {
            throw new IllegalArgumentException("amountAvailable cannot be negative: " + amountAvailable);
        }
    }
}
