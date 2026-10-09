package com.example;

import com.example.CLI.CLI;
import com.example.shop.cart.Cart;
import com.example.shop.cart.CartManager;
import com.example.shop.cart.InMemoryCartRepository;
import com.example.shop.customer.Customer;
import com.example.shop.customer.CustomerManager;
import com.example.shop.customer.InMemoryCustomerRepository;
import com.example.shop.order.InMemoryOrderRepository;
import com.example.shop.order.Order;
import com.example.shop.order.OrderManager;
import com.example.shop.product.InMemoryProductRepository;
import com.example.shop.product.Product;
import com.example.shop.product.ProductManager;
import com.example.shop.product.computer.Computer;
import com.example.shop.product.computer.components.PCCase;
import com.example.shop.product.computer.components.Processor;
import com.example.shop.product.computer.components.RAM;
import com.example.shop.product.other.Electronics;
import com.example.shop.product.smartphone.Smartphone;
import com.example.shop.product.smartphone.components.Accessory;
import com.example.shop.product.smartphone.components.BatteryCapacity;
import com.example.shop.product.smartphone.components.Color;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void generateInitialData(InMemoryCustomerRepository customerRepository, InMemoryCartRepository cartRepository, InMemoryOrderRepository orderRepository, InMemoryProductRepository productRepository) {
        Customer customer = new Customer("Jan", "Chrzan", "Jan@Chrzan.pl", "123456", "123456789");
        customerRepository.add(customer);
        customerRepository.add(new Customer("Rychu", "Peja", "Rychu@Chrzan.pl", "654321", "0987654321"));

        Product smartphone = new Smartphone("trapPhone", new BigDecimal("4200"), 1, Accessory.CASE, BatteryCapacity.CAPACITY_1000MAH, Color.BLACK);
        productRepository.add(smartphone);
        productRepository.add(new Computer("Komputer dysk 1000", new BigDecimal("4200"), 10, new Processor(Processor.Producer.INTEL, Processor.Model.RYZEN_7_7800X3D, 16), new RAM(16, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST), new PCCase(20, 30)));
        productRepository.add(new Electronics("mp4", new BigDecimal("200"), 100));

        Cart cart = new Cart();
        cartRepository.add(cart);
        cart.setProducts(new ArrayList<>(List.of(smartphone, smartphone)));

        orderRepository.add(new Order(customer, cart));
    }

    public static void main(String[] args) {
        InMemoryCartRepository cartRepository = new InMemoryCartRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository();
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();

        generateInitialData(customerRepository, cartRepository, orderRepository, productRepository);

        new CLI(cartRepository, orderRepository, customerRepository, productRepository);
    }
}