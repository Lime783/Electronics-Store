package com.example.shop.pricing;

import com.example.shop.order.Order;

public interface PricingPolicy {
    void calculatePrice(Order order);
}
