package com.example.order;

import com.example.cart.Cart;
import com.example.customer.Customer;
import com.example.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {
    private final UUID id;
    private final Customer customer;
    private final Cart cart;
    private final BigDecimal totalPrice;
    private OrderStatus orderStatus;

    public Order(Customer customer, Cart cart) {
        requireData(customer, cart);

        this.id = UUID.randomUUID();
        this.customer = customer;
        this.cart = cart;
        this.totalPrice = calculateTotalPrice();
        this.orderStatus = OrderStatus.PENDING;
    }

    private void requireData(Customer customer, Cart cart) {
        Objects.requireNonNull(customer, "Customer cannot be null");
        Objects.requireNonNull(cart, "Cart cannot be null");
    }

    private BigDecimal calculateTotalPrice() {
        return cart.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
