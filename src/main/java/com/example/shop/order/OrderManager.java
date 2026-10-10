package com.example.shop.order;

import com.example.shop.pricing.RegularPricing;
import com.example.shop.pricing.discount.BulkBuyPromo;
import com.example.shop.pricing.discount.EveningPromo;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        boolean isOrderAfter16 = order.getOrderDate().toLocalTime().isAfter(LocalTime.of(16, 0));
        boolean hasOrder3ProductsOrMore = order.getCart().getProducts().size() >= 3;
        if (hasOrder3ProductsOrMore) {
            order.setPricingPolicy(new BulkBuyPromo());
        } else if (isOrderAfter16) {
            order.setPricingPolicy(new EveningPromo());
        } else {
            order.setPricingPolicy(new RegularPricing());
        }
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