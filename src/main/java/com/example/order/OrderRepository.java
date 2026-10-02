package com.example.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void add(Order order);

    void deleteByID(UUID id);

    Optional<Order> findOrderByID(UUID id);

    Order getOrderByID(UUID id);

    List<Order> getAllOrders();
}
