package com.example.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders;

    public InMemoryOrderRepository() {
        this.orders = new ArrayList<>();
    }

    @Override
    public void add(Order orderToAdd) {
        orders.add(orderToAdd);
    }

    @Override
    public void deleteByID(UUID id) {
        orders.removeIf(order -> order.getId().equals(id));
    }

    @Override
    public Optional<Order> findOrderByID(UUID id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public Order getOrderByID(UUID id) {
        return findOrderByID(id).orElseThrow();
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }
}
