package com.example.shop.invoice;

import com.example.shop.cart.Cart;
import com.example.shop.customer.Customer;
import com.example.shop.order.InMemoryOrderRepository;
import com.example.shop.order.Order;
import com.example.shop.order.OrderManager;
import com.example.shop.product.Product;
import com.example.shop.product.computer.Computer;
import com.example.shop.product.computer.components.PCCase;
import com.example.shop.product.computer.components.Processor;
import com.example.shop.product.computer.components.RAM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceTest {

    private InMemoryOrderRepository inMemoryOrderRepository;
    private OrderManager orderManager;
    private InvoiceManager invoiceManager;

    private final Customer customer = new Customer("Jan", "Chrzan", "Jan@Chrzan.pl", "123456", "123456789");
    private final Product computer = new Computer("Komputer", new BigDecimal("123"), 10, new Processor(Processor.Producer.INTEL, Processor.Model.RYZEN_7_7800X3D, 16), new RAM(256, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST), new PCCase(1000, 1000));
    Cart cart = new Cart();

    @BeforeEach
    void setUp() {
        inMemoryOrderRepository = new InMemoryOrderRepository();
        orderManager = new OrderManager(inMemoryOrderRepository);
        invoiceManager = new InvoiceManager();
    }

    @Test
    void shouldCreateInvoice(){
        // Given
        cart.setProducts(List.of(computer));
        Order order = new Order(customer, cart);
        orderManager.addOrderToDatabase(order);

        // When
        Invoice invoice = invoiceManager.generateInvoice(order);

        // Then
        assertThat(invoice.getId()).isEqualTo(order.getId());
        assertThat(invoice.getOrder()).isEqualTo(order);
        assertThat(invoice.getDate().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}