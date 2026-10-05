package com.example.invoice;

import com.example.cart.Cart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class Invoice {
    private final UUID id;
    private final Cart cart;
    private final LocalDateTime date;

    public Invoice(Cart cart){
        Objects.requireNonNull(cart);

        this.id = cart.getId();
        this.cart = cart;
        this.date = LocalDateTime.now();
    }
}
