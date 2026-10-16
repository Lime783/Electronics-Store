package com.example.shop.pricing;

import com.example.shop.order.Order;

public interface PricingPolicy {
    boolean isApplicable(Order order);
    void calculatePrice(Order order);
}
