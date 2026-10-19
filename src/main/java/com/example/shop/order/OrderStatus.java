package com.example.shop.order;

import java.util.Set;

public enum OrderStatus { // state
    PENDING {
        @Override
        Set<OrderStatus> next() {
            return Set.of(CONFIRMED, CANCELLED);
        }
    },
    CONFIRMED {
        @Override
        Set<OrderStatus> next() {
            return Set.of(CANCELLED, COMPLETED);
        }
    },
    CANCELLED {
        @Override
        Set<OrderStatus> next() {
            return Set.of();
        }
    },
    COMPLETED {
        @Override
        Set<OrderStatus> next() {
            return Set.of();
        }
    };

    abstract Set<OrderStatus> next();

    OrderStatus transitionTo(OrderStatus target) {
        if (!(next().contains(target))) {
            throw new IllegalStateException(
                    "cannot go from %s to %s (allowed: %s)".formatted(this, target, next()));
        }
        return target;
    }
}