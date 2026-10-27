package com.example.shop.order;

import com.example.shop.pricing.Discount;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

import static com.example.archive.ArchiveUtils.addToArchive;

public class OrderManager {
    private final OrderRepository inMemoryOrderRepository;

    public OrderManager(OrderRepository inMemoryOrderRepository) {
        this.inMemoryOrderRepository = inMemoryOrderRepository;
    }

    public void addOrderToDatabase(Order order) {
        determineBestDiscount(order);
        order.calculateTotalPrice();
        String messageForArchiving = "%s - Created PENDING order with id %s containing %s".formatted(order.getOrderDate().truncatedTo(ChronoUnit.SECONDS), order.getId(), order.getCart().getProducts());
        addToArchive(messageForArchiving);
        inMemoryOrderRepository.add(order);
    }

    public Discount determineBestDiscount(Order order) {
        Discount bestDiscount = Arrays.stream(Discount.values())
                .filter(discount -> discount.isApplicable(order))
                .max(Comparator.comparing(Discount::discountRate))
                .orElse(Discount.NONE);

        order.setPricingPolicy(bestDiscount);
        return bestDiscount;
    }

    public Order getOrderById(UUID id) {
        return inMemoryOrderRepository.getOrderByID(id);
    }
}