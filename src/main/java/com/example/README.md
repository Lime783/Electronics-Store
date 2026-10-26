# 1. Project Description

The aim of the project is to create a complete online store system that allows customers to browse and purchase
electronic products, such as computers, smartphones, and other electronic devices.

Customers can browse available products, add them to their shopping carts, remove products from their carts, and place
orders with or without an invoice.

## 1.1. Product Categories

| Product Category  | Additional Properties                                              |
|-------------------|--------------------------------------------------------------------|
| Computer          | Processor, amount of RAM, case                                     |
| Smartphone        | Color, battery capacity, additional accessories, e.g. a phone case |
| Other Electronics | Basic specifications                                               |

Each product also has an identifier, name, price, and available quantity.

## 1.2. Customer and Shopping Cart Management

Customers can use the store's functionality, manage their shopping carts, and place orders. The shopping cart allows
customers to store selected products and check the total value of their purchases.

| Element       | Description                                          |
|---------------|------------------------------------------------------|
| Customer      | Email address, phone number, and password            |
| Shopping Cart | Unique identifier, list of products, and total value |
| Cart Items    | Ability to add, view, and remove products            |

## 1.3. Order Fulfillment Process

Each order contains information about the customer, the associated shopping cart, and the order placement date.

Orders can have four possible statuses:

| Order Status | Description                                                    |
|--------------|----------------------------------------------------------------|
| `PENDING`    | Initial status assigned when an order is placed                |
| `CONFIRMED`  | The order is confirmed after a simulated payment has been made |
| `CANCELLED`  | The order has been cancelled                                   |
| `COMPLETED`  | The order fulfillment process has been completed               |

### Order Fulfillment Workflow

``` text
PENDING
     |
     | Payment simulation
     v
CONFIRMED
     |
     | Completion of fulfillment
     v
COMPLETED
````

The order starts in the pending state. After payment simulation, it can be confirmed, and once fulfillment is complete,
its status changes to completed. An order can also be cancelled in accordance with the system's business rules.

All status changes are recorded in the `archive.txt` file, together with a description of the order and the date of the
change.

## 1.4. Discount System

The store has a discount system that automatically selects the most advantageous promotion based on the time of purchase
and the number of products in the order.

| Condition                       | Discount |
|---------------------------------|----------|
| Purchase after 4:00 PM          | 15%      |
| Purchase of at least 3 products | 25%      |

Discounts are not cumulative. The system has been designed with future development in mind and allows additional
promotions to be introduced.

## 1.5. Multithreading and Archiving

The system supports the simultaneous processing of multiple orders using multithreading. Each order can be processed
independently of the others.

Key requirements:

- Independent processing of multiple orders.
- Safe handling of concurrent writes to the archive.

## 1.6. User Interface

The system can be tested intuitively using a CLI (Command-Line Interface), which allows users to operate the store from
a terminal.

| CLI Functionality        | Available Operations                                             |
|--------------------------|------------------------------------------------------------------|
| Product Browsing         | Displaying available products and their specifications           |
| Shopping Cart Management | Adding and removing products and checking the cart's total value |
| Order Placement          | Creating orders with or without an invoice                       |
| Payment Handling         | Simulating payments and confirming orders                        |
| Order Management         | Checking and changing order statuses                             |

# 2. Technical Solutions Used

## 2.1. Inheritance and Product Modeling

The project uses inheritance, in which specific product categories extend the abstract `Product` class. The base class
defines common product properties, such as the identifier, name, price, and available quantity.

``` java
public abstract class Product {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private int amountAvailable;
}
```

For example, the `Smartphone` class extends the `Product` class with properties specific to smartphones.

``` java
public class Smartphone extends Product {
    Accessory accessory;
    BatteryCapacity batteryCapacity;
    Color color;
}
```

This approach makes it easier to add new product categories without modifying the shared base class.

## 2.2. Financial Calculation Precision

The `BigDecimal` type is used to store prices and perform financial calculations instead of `double`. This helps avoid
common floating-point precision errors when calculating prices and discounts.

## 2.3. Repository Interfaces

The project uses interfaces to define the methods that must be implemented by repositories responsible for data
management.

An example is the `CartRepository` interface, which defines operations for adding, deleting, searching for, and
retrieving shopping carts.

``` java
public interface CartRepository {
    void add(Cart cart);

    void deleteByID(UUID id);

    Optional<Cart> findCartByID(UUID id);

    Cart getCartByID(UUID id);

    List<Cart> getAllCarts();
}
```

Separating interfaces from their implementations facilitates project development and testing, as well as potential
changes to the data storage mechanism.

## 2.4. Custom Exceptions

Custom exceptions with descriptive names are used to make error handling clearer and indicate the type of problem that
has occurred.

An example is `DuplicateException`, which is used to represent errors related to duplicate entries.

``` java
public class DuplicateException extends RuntimeException {...}
```

This approach makes errors easier to identify and allows their handling to be tailored to specific situations within the
system.

## 2.5. Multithreading

The system uses multithreading to process multiple orders simultaneously. Each order can be processed independently,
allowing the system to simulate parallel customer service.

``` java
try (ExecutorService pool = Executors.newFixedThreadPool(8)){
        orders.forEach(order -> pool.submit(() -> processOrder(order)));
    }
```

## 2.6. Atomic Archive Updates

Order status changes are saved to the `archive.txt` file. To reduce the risk of exposing an incomplete file, a mechanism
is used that writes data to a temporary file and then atomically replaces the target file.

``` java
Files.writeString(
    archiveTmp,
    message + "\n",
    StandardCharsets.UTF_8,
    StandardOpenOption.APPEND
);

Files.move(
    archiveTmp,
    archive,
    StandardCopyOption.REPLACE_EXISTING,
    StandardCopyOption.ATOMIC_MOVE
);
```

## 2.7. Extensible Discount System

The discount mechanism has been designed to support the addition of further promotions. The system evaluates the
purchase conditions and selects the most advantageous available discount instead of adding together all applicable
promotions.