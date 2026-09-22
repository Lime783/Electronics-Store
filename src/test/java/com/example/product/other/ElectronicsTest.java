package com.example.product.other;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ElectronicsTest {

    Electronics electronics;

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

}