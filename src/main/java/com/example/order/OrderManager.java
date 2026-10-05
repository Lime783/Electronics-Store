package com.example.order;

import com.example.customer.Customer;

import java.util.List;
import java.util.UUID;

public class OrderManager {
    private final InMemoryOrderRepository inMemoryOrderRepository;

    public OrderManager(InMemoryOrderRepository inMemoryOrderRepository) {
        this.inMemoryOrderRepository = inMemoryOrderRepository;
    }

    public void addOrderToDatabase(Order order) {
        inMemoryOrderRepository.add(order);
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