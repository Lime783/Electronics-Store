package com.example.order;

import java.util.List;

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

    public List<Order> getAllOrdersFromDatabase() {
        return inMemoryOrderRepository.getAllOrders();
    }

    public void confirmOrder(Order order) {
        Order orderToConfirm = inMemoryOrderRepository.getOrderByID(order.getId());

        if (!(orderToConfirm.getOrderStatus().equals(OrderStatus.PENDING))) {
            throw new IllegalStateException("Order: " + orderToConfirm.getId() + " cannot be confirmed, must be pending");
        }
        orderToConfirm.setOrderStatus(OrderStatus.CONFIRMED);
    }

    public void cancelOrder(Order order) {
        Order orderToCancel = inMemoryOrderRepository.getOrderByID(order.getId());

        if (!(orderToCancel.getOrderStatus().equals(OrderStatus.PENDING) || orderToCancel.getOrderStatus().equals(OrderStatus.CONFIRMED))) {
            throw new IllegalStateException("Order: " + orderToCancel.getId() + " cannot be cancelled, must be pending or confirmed");
        }
        orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
    }

    public void completeOrder(Order order) {
        Order orderToComplete = inMemoryOrderRepository.getOrderByID(order.getId());

        if (!(orderToComplete.getOrderStatus().equals(OrderStatus.CONFIRMED))) {
            throw new IllegalStateException("Order: " + orderToComplete.getId() + " cannot be completed, must be confirmed");
        }
        orderToComplete.setOrderStatus(OrderStatus.COMPLETED);
    }
}