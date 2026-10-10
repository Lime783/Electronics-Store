package com.example.shop.pricing;

import com.example.shop.order.Order;

import java.math.BigDecimal;

public class RegularPricing implements PricingPolicy{
    @Override
    public void calculatePrice(Order order) {
        final BigDecimal regularPrice = new BigDecimal("1.00");
        final BigDecimal discount = new BigDecimal("0.00");
        order.setTotalPrice(order.getTotalPrice().multiply(regularPrice.subtract(discount)));
    }
}
