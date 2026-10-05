package com.example.shop.cart;

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
@Setter
@EqualsAndHashCode
@ToString
public class Cart {
    private List<Product> products;
    private final UUID id;
    private BigDecimal value;

    public Cart() {
        this.products = new ArrayList<>();
        this.id = UUID.randomUUID();
        this.value = new BigDecimal(0);
    }
}
