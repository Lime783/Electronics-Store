package com.example.shop.product.other;

import com.example.shop.product.Product;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
public class Electronics extends Product {
    public Electronics(String name, BigDecimal price, int amountAvailable) {
        super(name, price, amountAvailable);
    }
}
