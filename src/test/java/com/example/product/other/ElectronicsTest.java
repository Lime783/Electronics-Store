package com.example.product.other;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElectronicsTest {

    private Electronics electronics;

    @Test
    void shouldCreateElectronicsSuccessfully() {
        // Given
        String name = "mp3";
        BigDecimal price = new BigDecimal("200");
        int amountAvailable = 1;

        // When
        electronics = new Electronics(name, price, amountAvailable);

        // Then
        assertThat(electronics.getName()).isEqualTo(name);
        assertThat(electronics.getPrice()).isEqualTo(price);
        assertThat(electronics.getAmountAvailable()).isEqualTo(amountAvailable);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Given
        String name = "mp3";
        BigDecimal price = new BigDecimal("-200");
        int amountAvailable = 1;

        // When and Then
        assertThatThrownBy(() -> new Electronics(name, price, amountAvailable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("price cannot be negative");
    }
}