package com.example.CLI;

import com.example.cart.Cart;
import com.example.cart.CartManager;
import com.example.cart.InMemoryCartRepository;
import com.example.customer.Customer;
import com.example.customer.CustomerManager;
import com.example.customer.InMemoryCustomerRepository;
import com.example.invoice.InvoiceManager;
import com.example.order.InMemoryOrderRepository;
import com.example.order.Order;
import com.example.order.OrderManager;
import com.example.product.InMemoryProductRepository;
import com.example.product.Product;
import com.example.product.ProductManager;
import com.example.product.computer.Computer;
import com.example.product.computer.components.PCCase;
import com.example.product.computer.components.Processor;
import com.example.product.computer.components.RAM;
import com.example.product.other.Electronics;
import com.example.product.smartphone.Smartphone;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class CLI {
    final Scanner scanner = new Scanner(System.in);

    @Getter
    private final InMemoryCartRepository cartRepository;
    @Getter
    private final InMemoryOrderRepository orderRepository;
    @Getter
    private final InMemoryCustomerRepository customerRepository;
    @Getter
    private final InMemoryProductRepository productRepository;

    private final CartManager cartManager;
    private final CustomerManager customerManager;
    private final OrderManager orderManager;
    private final ProductManager productManager;
    private final InvoiceManager invoiceManager;

    public CLI(InMemoryCartRepository inMemoryCartRepository, InMemoryOrderRepository inMemoryOrderRepository, InMemoryCustomerRepository inMemoryCustomerRepository, InMemoryProductRepository inMemoryProductRepository) {
        Objects.requireNonNull(inMemoryCartRepository);
        Objects.requireNonNull(inMemoryOrderRepository);
        Objects.requireNonNull(inMemoryCustomerRepository);
        Objects.requireNonNull(inMemoryProductRepository);

        this.cartRepository = inMemoryCartRepository;
        this.orderRepository = inMemoryOrderRepository;
        this.customerRepository = inMemoryCustomerRepository;
        this.productRepository = inMemoryProductRepository;

        this.cartManager = new CartManager(inMemoryCartRepository);
        this.customerManager = new CustomerManager(inMemoryCustomerRepository);
        this.orderManager = new OrderManager(inMemoryOrderRepository);
        this.productManager = new ProductManager(inMemoryProductRepository);
        this.invoiceManager = new InvoiceManager();

        while (true) {
            chooseWhatToDo();
            chooseCategory(scanner.nextLine());
        }

    }

    private static void chooseWhatToDo() {
        System.out.println("""
                
                What service do you want to use?
                1 - customers
                2 - products
                3 - carts
                4 - orders
                5 - list everything (RECOMMENDED)
                HELP
                QUIT
                """);
    }

    private void chooseCategory(String choice) {
        switch (choice.toUpperCase()) {
            case "1", "CUSTOMERS" -> showCustomersCategory();
            case "2", "PRODUCTS" -> showProductsCategory();
            case "3", "CARTS" -> showCartsCategory();
            case "4", "ORDERS" -> showOrdersCategory();
            case "5" -> listEverything();
            case "HELP" -> showHelp();
            case "QUIT" -> System.exit(0);
            default -> System.out.println("Unknown command, try again or use HELP");
        }
    }

    private void showCustomersCategory() {
        System.out.println("""
                What command do you want to use?
                1 - add customer
                2 - delete customer
                3 - list all customers
                """);
        switch (scanner.nextLine()) {
            case "1" -> addCustomer();
            case "2" -> deleteCustomer();
            case "3" -> listAllCustomers();
        }
    }

    private void addCustomer() {
        Customer customer = provideCustomerData();
        getCustomerRepository().add(customer);
    }

    private Customer provideCustomerData() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        return new Customer(
                firstName,
                lastName,
                email,
                password,
                phoneNumber);
    }

    private void deleteCustomer() {
        System.out.print("UUID of customer to delete: ");

        UUID id = UUID.fromString(scanner.next());

        customerManager.removeCustomerFromDatabase(
                customerManager.getCustomerById(id)
        );

        System.out.println("Successfully deleted: " + customerManager.getCustomerById(id).getFirstName() + " " + customerManager.getCustomerById(id).getLastName());
    }

    private void listAllCustomers() {
        customerRepository.getAllCustomers()
                .forEach(System.out::println);
    }

    private void showProductsCategory() {
        System.out.println("""
                What command do you want to use?
                1 - add product
                2 - delete product
                3 - list all products
                """);
        switch (scanner.nextLine()) {
            case "1" -> addProduct();
            case "2" -> deleteProduct();
            case "3" -> listAllProducts();
        }
    }

    private void addProduct() {
        System.out.println("""
                What kind of product do you want to add?
                1 - Computer
                2 - Smartphone
                3 - Other
                """);
        switch (scanner.nextLine()) {
            case "1" -> addComputer();
            case "2" -> addSmartphone();
            case "3" -> addOther();
        }
    }

    private void addComputer() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        System.out.print("Amount available: ");
        int amountAvailable = Integer.parseInt(scanner.nextLine());

        System.out.print("Processor (producer, ex. INTEL): ");
        Processor.Producer processorProducer = Processor.Producer.valueOf(scanner.nextLine());

        System.out.print("Processor (model, ex. CORE_I5_14600K): ");
        Processor.Model processorModel = Processor.Model.valueOf(scanner.nextLine());

        System.out.print("Processor (cores, ex. 8): ");
        int processorCores = Integer.parseInt(scanner.nextLine());

        Processor processor = new Processor(processorProducer,
                processorModel,
                processorCores);

        System.out.print("RAM (capacity in GB, ex. 256): ");
        int ramCapacity = Integer.parseInt(scanner.nextLine());

        System.out.print("RAM (producer, ex. KINGSTON): ");
        RAM.Producer ramProducer = RAM.Producer.valueOf(scanner.nextLine());

        System.out.print("RAM (model, ex. FURY_BEAST): ");
        RAM.Model ramModel = RAM.Model.valueOf(scanner.nextLine());

        RAM ram = new RAM(ramCapacity, ramProducer, ramModel);

        System.out.print("Case (width in cm): ");
        int caseWidth = Integer.parseInt(scanner.nextLine());

        System.out.print("Case (height in cm): ");
        int caseHeight = Integer.parseInt(scanner.nextLine());

        PCCase pcCase = new PCCase(caseWidth, caseHeight);

        productManager.addProductToDataBase(new Computer(name, price, amountAvailable, processor, ram, pcCase));
    }

    private void addSmartphone() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        System.out.print("Amount available: ");
        int amountAvailable = Integer.parseInt(scanner.nextLine());

        System.out.print("Accessory (ex. CASE): ");
        Accessory accessory = Accessory.valueOf(scanner.nextLine());

        System.out.print("Battery Capacity (ex, CAPACITY_1000MAH): ");
        BatteryCapacity batteryCapacity = BatteryCapacity.valueOf(scanner.nextLine());

        System.out.print("Color (ex, RED): ");
        Color color = Color.valueOf(scanner.nextLine());

        productManager.addProductToDataBase(new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color));
    }

    private void addOther() {
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        System.out.print("Amount available: ");
        int amountAvailable = Integer.parseInt(scanner.nextLine());

        productManager.addProductToDataBase(new Electronics(name, price, amountAvailable));
    }

    private void deleteProduct() {
        System.out.print("UUID of product to delete: ");

        UUID id = UUID.fromString(scanner.next());

        productManager.removeProductFromDataBase(
                productManager.getProductById(id)
        );
    }

    private void listAllProducts() {
        productRepository.getAllProducts().forEach(System.out::println);
    }

    private void showCartsCategory() {
        System.out.println("""
                What command do you want to use?
                1 - add cart
                2 - delete cart
                3 - list all carts
                4 - add product to cart
                """);
        switch (scanner.nextLine()) {
            case "1" -> addCart();
            case "2" -> deleteCart();
            case "3" -> listAllCarts();
            case "4" -> addProductToCart();
        }
    }

    private void addCart() {
        Cart cart = new Cart();
        System.out.println("Created cart with ID: " + cart.getId());
        getCartRepository().add(cart);
    }

    private void deleteCart() {
        System.out.print("UUID of cart to delete: ");

        UUID id = UUID.fromString(scanner.next());

        cartManager.removeCartFromDataBase(
                cartManager.getCartById(id)
        );
    }

    private void listAllCarts() {
        cartRepository.getAllCarts().forEach(System.out::println);
    }

    private void addProductToCart() {
        System.out.println("UUID of product to add: ");
        Product product = productManager.getProductById(UUID.fromString(scanner.next()));

        System.out.println("UUID of cart to add to: ");
        Cart cart = cartManager.getCartById(UUID.fromString(scanner.next()));

        cartManager.addProductToCart(product, cart);
    }

    private void showOrdersCategory() {
        System.out.println("""
                What command do you want to use?
                1 - create order
                2 - cancel order
                3 - list all orders
                4 - pay (confirm) for order
                5 - complete order
                6 - create invoice for an order
                """);
        switch (scanner.nextLine()) {
            case "1" -> createOrder();
            case "2" -> cancelOrder();
            case "3" -> listAllOrders();
            case "4" -> confirmOrder();
            case "5" -> completeOrder();
            case "6" -> createInvoice();
        }
    }

    private void createOrder() {
        System.out.print("UUID of customer: ");
        Customer customer = customerManager.getCustomerById(UUID.fromString(scanner.next()));

        System.out.print("UUID of cart: ");
        Cart cart = cartManager.getCartById(UUID.fromString(scanner.nextLine()));

        Order order = new Order(customer, cart);

        getOrderRepository().add(order);
    }

    private void cancelOrder() {
        System.out.print("UUID of order to cancel: ");

        Order orderToCancel = orderManager.getOrderById(UUID.fromString(scanner.next()));

        orderToCancel.cancel();
    }

    private void listAllOrders() {
        orderRepository.getAllOrders().forEach(System.out::println);
    }

    private void confirmOrder(){
        System.out.print("UUID of order to pay for: ");

        Order orderToConfirm = orderManager.getOrderById(UUID.fromString(scanner.next()));

        System.out.print("Total to pay: " + orderToConfirm.getTotalPrice() + ", would you like to pay? (Y/N)");
        if (scanner.next().equalsIgnoreCase("Y")) {
            orderToConfirm.confirm();
            System.out.print("Payment successful for order: " + orderToConfirm.getId());
        } else {
            System.out.print("Bruh");
        }
    }

    private void completeOrder(){
        System.out.print("UUID of order to complete: ");

        Order orderToComplete = orderManager.getOrderById(UUID.fromString(scanner.next()));

        orderToComplete.complete();
    }

    private void createInvoice(){
        System.out.print("UUID of order to generate invoice for: ");

        Order orderToInvoice = orderManager.getOrderById(UUID.fromString(scanner.next()));

        invoiceManager.generateInvoice(orderToInvoice);
    }

    private void showHelp() {
        System.out.println("""
            
            ==================== HELP ====================
            
            MAIN MENU
            1 / CUSTOMERS  - manage customers
            2 / PRODUCTS   - manage products
            3 / CARTS      - manage shopping carts
            4 / ORDERS     - manage orders
            5             - list everything
            HELP          - show this help
            QUIT          - exit the application
            
            CUSTOMERS
            1 - add customer
            2 - delete customer
            3 - list all customers
            
            PRODUCTS
            1 - add product
            2 - delete product
            3 - list all products
            
            Product types:
            1 - Computer
            2 - Smartphone
            3 - Other
            
            CARTS
            1 - create cart
            2 - delete cart
            3 - list all carts
            4 - add product to cart
            
            ORDERS
            1 - create order
            2 - cancel order
            3 - list all orders
            4 - pay / confirm order
            5 - complete order
            
            ORDER FLOW
            Create order -> Pay / Confirm -> Complete
            An order can also be cancelled.
            
            TIP
            UUIDs are required when deleting or modifying
            customers, products, carts and orders.
            
            ==============================================
            """);
    }


    private void listEverything() {
        System.out.println("\n----------------PRODUCTS----------------\n");
        listAllProducts();
        System.out.println("\n----------------CUSTOMERS----------------\n");
        listAllCustomers();
        System.out.println("\n----------------CARTS----------------\n");
        listAllCarts();
        System.out.println("\n----------------ORDERS----------------\n");
        listAllOrders();
    }
}

