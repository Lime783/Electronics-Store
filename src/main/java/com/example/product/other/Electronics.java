package com.example.product.other;

import com.example.product.Product;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

public class Electronics extends Product {
    public Electronics(String name, BigDecimal price, int amountAvailable) {
        super(name, price, amountAvailable);
    }
}
