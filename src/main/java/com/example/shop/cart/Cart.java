package com.example.shop.cart;

import com.example.exceptions.NegativeValueException;
import com.example.shop.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class Cart {
    private final UUID id;
    @Setter
    private List<Product> products;
    private BigDecimal value;

    public Cart() {
        this.id = UUID.randomUUID();
        this.products = new ArrayList<>();
        this.value = new BigDecimal(0);
    }

    public void setValue(BigDecimal newValue) {
        if (newValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeValueException("The product value cannot be less than zero: " + newValue);
        }
        this.value = newValue;
    }
}
