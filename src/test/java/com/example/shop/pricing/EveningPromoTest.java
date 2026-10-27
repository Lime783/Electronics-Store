package com.example.shop.pricing;

import com.example.clock.Clock;
import com.example.shop.cart.Cart;
import com.example.shop.customer.Customer;
import com.example.shop.order.InMemoryOrderRepository;
import com.example.shop.order.Order;
import com.example.shop.order.OrderManager;
import com.example.shop.order.OrderRepository;
import com.example.shop.product.Product;
import com.example.shop.product.computer.Computer;
import com.example.shop.product.computer.components.PCCase;
import com.example.shop.product.computer.components.Processor;
import com.example.shop.product.computer.components.RAM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EveningPromoTest {
    Clock clock = Clock.INSTANCE;
    private OrderRepository inMemoryOrderRepository;
    private OrderManager orderManager;
    private Customer customer = new Customer("Jan", "Chrzan", "Jan@Chrzan.pl", "123456", "123456789");
    private Product computer = new Computer("Komputer", new BigDecimal("100"), 10, new Processor(Processor.Producer.INTEL, Processor.Model.RYZEN_7_7800X3D, 16), new RAM(256, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST), new PCCase(1000, 1000));

    private final ZonedDateTime before4PM = LocalDateTime.of(2026, 6, 7, 15, 0).atZone(clock.zoneId());
    private final ZonedDateTime exactly4PM = LocalDateTime.of(2026, 6, 7, 16, 0).atZone(clock.zoneId());
    private final ZonedDateTime after4PM = LocalDateTime.of(2026, 6, 7, 17, 0).atZone(clock.zoneId());
    Cart cart = new Cart();

    @BeforeEach
    void setUp() {
        inMemoryOrderRepository = new InMemoryOrderRepository();
        orderManager = new OrderManager(inMemoryOrderRepository);
    }

    @Test
    void shouldApplyEveningPromoAfter4PM() {
        // Given
        cart.setProducts(List.of(computer));
        Order order = new Order(customer, cart, after4PM);

        // When
        orderManager.addOrderToDatabase(order);

        // Then
        assertThat(order.getPricingPolicy()).isEqualTo(Discount.EVENING);
        assertThat(order.getTotalPrice()).isLessThan(new BigDecimal("90.01"));
    }

    @Test
    void shouldApplyEveningPromoExactlyAt4PM() {
        // Given
        cart.setProducts(List.of(computer));
        Order order = new Order(customer, cart, exactly4PM);

        // When
        orderManager.addOrderToDatabase(order);

        // Then
        assertThat(order.getPricingPolicy()).isEqualTo(Discount.EVENING);
        assertThat(order.getTotalPrice()).isLessThan(new BigDecimal("90.01"));
    }

    @Test
    void shouldNotApplyEveningPromoAfter4PM() {
        // Given
        cart.setProducts(List.of(computer));
        Order order = new Order(customer, cart, before4PM);

        // When
        orderManager.addOrderToDatabase(order);

        // Then
        assertThat(order.getPricingPolicy()).isNotEqualTo(Discount.EVENING);
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("100.00"));
    }
}