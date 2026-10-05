package com.example.product.smartphone;

import com.example.shop.product.smartphone.Smartphone;
import com.example.shop.product.smartphone.components.Accessory;
import com.example.shop.product.smartphone.components.BatteryCapacity;
import com.example.shop.product.smartphone.components.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SmartphoneTest {

    private Smartphone smartphone;

    @Test
    void shouldCreateSmartphoneSuccessfully() {
        // Given
        String name = "trapPhone";
        BigDecimal price = new BigDecimal("420.00");
        int amountAvailable = 10;
        Accessory accessory = Accessory.CASE;
        BatteryCapacity batteryCapacity = BatteryCapacity.CAPACITY_1000MAH;
        Color color = Color.RED;

        // When
        smartphone = new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color);

        // Then
        assertThat(smartphone.getName()).isEqualTo(name);
        assertThat(smartphone.getPrice()).isEqualTo(price);
        assertThat(smartphone.getAmountAvailable()).isEqualTo(amountAvailable);
        assertThat(smartphone.getAccessory()).isEqualTo(accessory);
        assertThat(smartphone.getBatteryCapacity()).isEqualTo(batteryCapacity);
        assertThat(smartphone.getColor()).isEqualTo(color);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Given
        String name = "trapPhone";
        BigDecimal price = new BigDecimal("-420.00");
        int amountAvailable = 10;
        Accessory accessory = Accessory.CASE;
        BatteryCapacity batteryCapacity = BatteryCapacity.CAPACITY_1000MAH;
        Color color = Color.RED;

        // When and Then
        assertThatThrownBy(() -> new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("price cannot be negative");
    }
}