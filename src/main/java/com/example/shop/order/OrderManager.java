package com.example.shop.order;

import com.example.shop.pricing.Discount;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.example.archive.ArchiveUtils.addToArchive;

public class OrderManager {
    private final InMemoryOrderRepository inMemoryOrderRepository;

    public OrderManager(InMemoryOrderRepository inMemoryOrderRepository) {
        this.inMemoryOrderRepository = inMemoryOrderRepository;
    }

    public void addOrderToDatabase(Order order) {
        determineBestDiscount(order);
        order.getPricingPolicy().calculatePrice(order);
        String messageForArchiving = "%s - Created PENDING order with id %s containing %s".formatted(order.getOrderDate().truncatedTo(ChronoUnit.SECONDS), order.getId(), order.getCart().getProducts());
        addToArchive(messageForArchiving);
        inMemoryOrderRepository.add(order);
    }

    public void determineBestDiscount(Order order) {
        Discount bestDiscount = Arrays.stream(Discount.values())
                .filter(discount -> discount.isApplicable(order))
                .max(Comparator.comparing(Discount::getDiscountRate))
                .orElse(Discount.NONE);

        order.setPricingPolicy(bestDiscount);
    }

    public void removeOrderFromDatabase(Order order) {
        inMemoryOrderRepository.deleteByID(order.getId());
    }

    public Order getOrderById(UUID id) {
        return inMemoryOrderRepository.getOrderByID(id);
    }

    public List<Order> getAllOrdersFromDatabase() {
        return inMemoryOrderRepository.getAllOrders();
    }
}