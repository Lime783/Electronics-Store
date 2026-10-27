package com.example.shop.pricing;

import com.example.shop.order.Order;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Discount implements PricingPolicy { // strategy

    NONE("-0% always!") {
        @Override
        public boolean isApplicable(Order order) {
            return true;
        }

        @Override
        public BigDecimal discountRate() {
            return BigDecimal.ZERO;
        }
    },

    EVENING("-15% after 4PM") {
        @Override
        public boolean isApplicable(Order order) {
            return order.getOrderDate().getHour() >= EVENING_HOUR_PROMO_THRESHOLD;
        }

        @Override
        public BigDecimal discountRate() {
            return new BigDecimal("0.15");
        }
    },

    BULK("-25% for at least 3 pcs") {
        @Override
        public boolean isApplicable(Order order) {
            return order.getCart().getProducts().size() >= BULK_BUY_PROMO_THRESHOLD;
        }

        @Override
        public BigDecimal discountRate() {
            return new BigDecimal("0.25");
        }
    };

    private static final int EVENING_HOUR_PROMO_THRESHOLD = 16;
    private static final int BULK_BUY_PROMO_THRESHOLD = 3;

    private final String description;

    Discount(String description) {
        this.description = description;
    }
}
