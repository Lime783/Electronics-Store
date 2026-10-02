package com.example.order;

import com.example.cart.Cart;
import com.example.customer.Customer;
import com.example.product.Product;
import com.example.product.computer.Computer;
import com.example.product.computer.components.PCCase;
import com.example.product.computer.components.Processor;
import com.example.product.computer.components.RAM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    private InMemoryOrderRepository inMemoryOrderRepository;
    private OrderManager orderManager;

    private Customer customer = new Customer("Jan", "Chrzan", "Jan@Chrzan.pl", "123456", "123456789");
    private Product computer = new Computer("Komputer", new BigDecimal("123"), 10, new Processor(Processor.Producer.INTEL, Processor.Model.RYZEN_7_7800X3D, 16), new RAM(256, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST), new PCCase(1000, 1000));
    Cart cart = new Cart();

    @BeforeEach
    void setUp() {
        inMemoryOrderRepository = new InMemoryOrderRepository();
        orderManager = new OrderManager(inMemoryOrderRepository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        cart.setProducts(List.of(computer));
        Order order = new Order(customer, cart);

        // When
        orderManager.addOrderToDatabase(order);

        // Then
        assertThat(order).isEqualTo(inMemoryOrderRepository.getOrderByID(order.getId()));
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoCustomer() {
        // Given
        cart.setProducts(List.of(computer));

        // When and Then
        assertThatThrownBy(() -> new Order(null, cart))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoCart() {
        assertThatThrownBy(() -> new Order(customer, null))
                .isInstanceOf(NullPointerException.class);
    }
}