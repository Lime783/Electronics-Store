package com.example;

import com.example.shop.cart.Cart;
import com.example.shop.cart.CartManager;
import com.example.shop.cart.InMemoryCartRepository;
import com.example.shop.customer.Customer;
import com.example.shop.customer.CustomerManager;
import com.example.shop.customer.InMemoryCustomerRepository;
import com.example.shop.order.Order;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.archive.ArchiveUtils.resetArchive;
import static org.assertj.core.api.Assertions.assertThat;

class SyncVsAsyncTest {
    static private final int HOW_MANY_ORDERS = 30;
    static private final int MAX_ITEMS_IN_CART = 5;
    static private final int AMOUNT_OF_PRODUCTS_AVAILABLE = HOW_MANY_ORDERS * MAX_ITEMS_IN_CART;
    static private final Random random = new Random();

    private List<Order> orders;

    InMemoryCartRepository cartRepository = new InMemoryCartRepository();
    InMemoryProductRepository productRepository = new InMemoryProductRepository();
    InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository();

    ProductManager productManager = new ProductManager(productRepository);
    CustomerManager customerManager = new CustomerManager(customerRepository);
    CartManager cartManager = new CartManager(cartRepository);

    public void generateInitialData() {
        addCustomersToDataBase();
        addProductsToDataBase();

        this.orders = new ArrayList<>(HOW_MANY_ORDERS);

        int howManyProductsInDataBase = productManager.getAllProductsFromDataBase().size();
        int howManyCustomersInDataBase = customerManager.getAllCustomersFromDatabase().size();

        for (int i = 0; i < HOW_MANY_ORDERS; i++) {
            orders.add(generateRandomOrder(howManyProductsInDataBase, howManyCustomersInDataBase));
        }
    }

    private Order generateRandomOrder(int howManyProductsInDataBase, int howManyCustomersInDataBase) {
        Cart randomCart = new Cart();
        int howManyProductsInCart = random.nextInt(MAX_ITEMS_IN_CART) + 1;
        fillUpCartWithRandomProducts(howManyProductsInDataBase, howManyProductsInCart, randomCart);
        Customer randomCustomer = customerManager.getAllCustomersFromDatabase().get(random.nextInt(howManyCustomersInDataBase));
        return new Order(randomCustomer, randomCart);
    }

    private void fillUpCartWithRandomProducts(int howManyProductsInDataBase, int howManyProductsInCart, Cart randomCart) {
        for (int j = 0; j < howManyProductsInCart; j++) {
            Product randomProduct = productManager.getAllProductsFromDataBase().get(random.nextInt(howManyProductsInDataBase));
            cartManager.addProductToCart(randomProduct, randomCart);
        }
    }

    private void addProductsToDataBase() {
        productManager.addProductToDataBase(new Smartphone("trapPhone", new BigDecimal("2400.99"), AMOUNT_OF_PRODUCTS_AVAILABLE, Accessory.CASE, BatteryCapacity.CAPACITY_1000MAH, Color.BLACK));
        productManager.addProductToDataBase(new Computer("Komputer dysk 1000", new BigDecimal("4201"), AMOUNT_OF_PRODUCTS_AVAILABLE, new Processor(Processor.Producer.INTEL, Processor.Model.RYZEN_7_7800X3D, 16), new RAM(16, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST), new PCCase(20, 30)));
        productManager.addProductToDataBase(new Electronics("mp4", new BigDecimal("400"), AMOUNT_OF_PRODUCTS_AVAILABLE));
        productManager.addProductToDataBase(new Electronics("mp5", new BigDecimal("500"), AMOUNT_OF_PRODUCTS_AVAILABLE));
        productManager.addProductToDataBase(new Electronics("mp6", new BigDecimal("600"), AMOUNT_OF_PRODUCTS_AVAILABLE));
    }

    private void addCustomersToDataBase() {
        customerManager.addCustomerToDatabase(new Customer("Jan Juan Buan Huan", "Chrzan-Chrzanowski", "Jan@Chrzan.pl", "123456", "123456789"));
        customerManager.addCustomerToDatabase(new Customer("Bartek", "Chrzan", "Jan@Chrzan.pl", "123456", "213456789"));
        customerManager.addCustomerToDatabase(new Customer("Czarek", "Chrzan", "Jan@Chrzan.pl", "123456", "132456789"));
        customerManager.addCustomerToDatabase(new Customer("Dawid", "Chrzan", "Jan@Chrzan.pl", "123456", "125456789"));
        customerManager.addCustomerToDatabase(new Customer("Rychu", "Peja", "Rychu@Chrzan.pl", "654321", "0987654321"));
    }

    public long processOrdersSync(List<Order> orders) {
        long start = System.nanoTime();
        orders.forEach(this::processOrder);
        long end = System.nanoTime();
        return end - start;
    }

    private void processOrder(Order order) {
        order.confirm();
        doLongOperation();
        order.complete();
    }

    public long processOrdersAsync(List<Order> orders) {
        long start = System.nanoTime();

        try (ExecutorService pool = Executors.newFixedThreadPool(8)){
            orders.forEach(order -> pool.submit(() -> processOrder(order)));
        }

        long end = System.nanoTime();
        return end - start;
    }

    private void doLongOperation() {
        int howLongToWait = random.nextInt(101) + 100;
        try {
            Thread.sleep(howLongToWait);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    public void tearDown() {
        resetArchive();
    }

    @Test
    void shouldProcessingOrdersAsyncBeFasterThanSync() {
        // Given
        generateInitialData();

        // When
        long syncTime = processOrdersSync(orders);
        long asyncTime = processOrdersAsync(orders);

        // Then
        assertThat(asyncTime).isLessThan(syncTime);
    }
}