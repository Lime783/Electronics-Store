package com.example.shop.invoice;

import com.example.shop.order.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class Invoice {
    private final UUID id;
    private final Order order;
    private final LocalDateTime date;

    public Invoice(Order order) {
        Objects.requireNonNull(order);

        this.id = order.getId();
        this.order = order;
        this.date = LocalDateTime.now();
    }
}
