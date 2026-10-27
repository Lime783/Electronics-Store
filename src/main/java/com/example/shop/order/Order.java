package com.example.shop.order;

import com.example.shop.cart.Cart;
import com.example.shop.customer.Customer;
import com.example.shop.pricing.Discount;
import com.example.shop.pricing.PricingPolicy;
import com.example.shop.product.Product;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.archive.ArchiveUtils.addToArchive;

@Getter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Order {
    private final UUID id;
    private final Customer customer;
    private final Cart cart;
    private final List<Product> products;
    private OrderStatus orderStatus;
    private final LocalDateTime orderDate;
    private PricingPolicy pricingPolicy;
    private BigDecimal totalPrice;

    @Builder // builder
    public Order(Customer customer, Cart cart, ZonedDateTime orderDate) {
        requireData(customer, cart);

        if (cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot create an order with empty cart");
        }

        this.id = UUID.randomUUID();
        this.customer = customer;
        this.cart = cart;
        this.products = List.copyOf(cart.getProducts());
        this.orderStatus = OrderStatus.PENDING;
        this.orderDate = orderDate.toLocalDateTime();
        this.pricingPolicy = Discount.NONE;
        this.totalPrice = BigDecimal.ZERO;
    }

    public Order(Customer customer, Cart cart) {
        this(customer, cart, ZonedDateTime.now());
    }

    private void requireData(Customer customer, Cart cart) {
        Objects.requireNonNull(customer, "Customer cannot be null");
        Objects.requireNonNull(cart, "Cart cannot be null");
    }

    public void calculateTotalPrice() {
        totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(BigDecimal.ONE.subtract(pricingPolicy.discountRate()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void confirm() { // state
        changeStatusTo(OrderStatus.CONFIRMED);
    }

    public void cancel() {
        changeStatusTo(OrderStatus.CANCELLED);
        restoreProductsFromCart();
    }

    private void restoreProductsFromCart() {
        products.forEach(product -> product.addAmountAvailable(1));
    }

    public void complete() {
        changeStatusTo(OrderStatus.COMPLETED);
    }

    private void changeStatusTo(OrderStatus nextOrderStatus) {
        orderStatus = orderStatus.transitionTo(nextOrderStatus);
        String messageForArchiving = "%s - %s order with id %s ".formatted(getOrderDate().truncatedTo(ChronoUnit.SECONDS), nextOrderStatus, getId());
        addToArchive(messageForArchiving);
    }

    void setPricingPolicy(Discount pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }
}
