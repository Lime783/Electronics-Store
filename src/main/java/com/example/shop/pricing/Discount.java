package com.example.shop.pricing;

import com.example.shop.order.Order;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Discount implements PricingPolicy { // strategy

    NONE("-0% always!", BigDecimal.ZERO) {
        @Override
        public boolean isApplicable(Order order) {
            return true;
        }

        @Override
        public void calculatePrice(Order order) {
            setDiscount(order, getDiscountRate());
        }
    },

    EVENING("-15% after 4PM", new BigDecimal("0.15")) {
        @Override
        public boolean isApplicable(Order order) {
            return order.getOrderDate().getHour() >= 16;
        }

        @Override
        public void calculatePrice(Order order) {
            setDiscount(order, getDiscountRate());
        }
    },

    BULK("-25% for at least 3 pcs", new BigDecimal("0.25")) {
        @Override
        public boolean isApplicable(Order order) {
            return order.getCart().getProducts().size() >= 3;
        }

        @Override
        public void calculatePrice(Order order) {
            setDiscount(order, getDiscountRate());
        }
    };

    private static final BigDecimal basePrice = new BigDecimal("1.00");

    private static void setDiscount(Order order, BigDecimal discount) {
        order.setTotalPrice(order.getTotalPrice().multiply(basePrice.subtract(discount)));
    }

    private final String description;
    private final BigDecimal discountRate;

    Discount(String description, BigDecimal discountRate) {
        this.description = description;
        this.discountRate = discountRate;
    }

    public String description() {
        return description;
    }
}
