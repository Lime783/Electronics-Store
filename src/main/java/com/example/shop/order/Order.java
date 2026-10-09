package com.example.shop.order;

import com.example.shop.cart.Cart;
import com.example.shop.customer.Customer;
import com.example.shop.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import static com.example.archive.ArchiveUtils.addToArchive;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Order {
    private final UUID id;
    private final Customer customer;
    private final Cart cart;
    private final BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private final LocalDateTime orderDate;

    public Order(Customer customer, Cart cart) {
        requireData(customer, cart);

        this.id = UUID.randomUUID();
        this.customer = customer;
        this.cart = cart;
        this.totalPrice = calculateTotalPrice();
        this.orderStatus = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
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

    public void confirm() {
        if (!(getOrderStatus().equals(OrderStatus.PENDING))) {
            throw new IllegalStateException("Order: " + getId() + " cannot be confirmed, must be pending");
        }

        String messageForArchiving = "%s - CONFIRMED order with id %s ".formatted(getOrderDate().truncatedTo(ChronoUnit.SECONDS), getId());
        addToArchive(messageForArchiving);
        setOrderStatus(OrderStatus.CONFIRMED);
    }

    public void cancel() {
        if (!(getOrderStatus().equals(OrderStatus.PENDING) || getOrderStatus().equals(OrderStatus.CONFIRMED))) {
            throw new IllegalStateException("Order: " + getId() + " cannot be cancelled, must be pending or confirmed");
        }

        String messageForArchiving = "%s - CANCELLED order with id %s ".formatted(getOrderDate().truncatedTo(ChronoUnit.SECONDS), getId());
        addToArchive(messageForArchiving);
        setOrderStatus(OrderStatus.CANCELLED);
    }

    public void complete() {
        if (!(getOrderStatus().equals(OrderStatus.CONFIRMED))) {
            throw new IllegalStateException("Order: " + getId() + " cannot be completed, must be confirmed");
        }

        String messageForArchiving = "%s - COMPLETED order with id %s ".formatted(getOrderDate().truncatedTo(ChronoUnit.SECONDS), getId());
        addToArchive(messageForArchiving);
        setOrderStatus(OrderStatus.COMPLETED);
    }
}
