package com.example.shop.pricing.discount;

import com.example.shop.order.Order;
import com.example.shop.pricing.PricingPolicy;

import java.math.BigDecimal;

public class EveningPromo implements PricingPolicy {
    @Override
    public void calculatePrice(Order order) {
        final BigDecimal regularPrice = new BigDecimal("1.00");
        final BigDecimal discount = new BigDecimal("0.15");
        order.setTotalPrice(order.getTotalPrice().multiply(regularPrice.subtract(discount)));
    }
}
