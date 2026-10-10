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
    private final UUID id;
    private List<Product> products;
    private BigDecimal value;

    public Cart() {
        this.id = UUID.randomUUID();
        this.products = new ArrayList<>();
        this.value = new BigDecimal(0);
    }
}
