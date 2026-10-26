package com.example.shop.pricing;

import com.example.shop.order.Order;

import java.math.BigDecimal;

public interface PricingPolicy {
    boolean isApplicable(Order order);

    BigDecimal discountRate();
}
