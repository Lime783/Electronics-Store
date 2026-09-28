package com.example.product.computer;

import com.example.product.computer.components.PCCase;
import com.example.product.computer.components.Processor;
import com.example.product.computer.components.RAM;
import com.example.product.smartphone.Smartphone;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ComputerTest {

    private Computer computer;

    @Test
    void shouldCreateComputerSuccessfully(){
        // Given
        String name = "Konkuter dysk tysionc";
        BigDecimal price = new BigDecimal("100");
        int amountAvailable = 100;
        Processor processor = new Processor(Processor.Producer.INTEL, Processor.Model.CORE_I7_14700K, 4);
        RAM ram = new RAM(8, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST);
        PCCase pcCase = new PCCase(2048, 1024);

        // When
        computer = new Computer(name, price, amountAvailable, processor, ram, pcCase);

        // Then
        assertThat(computer.getName()).isEqualTo(name);
        assertThat(computer.getPrice()).isEqualTo(price);
        assertThat(computer.getAmountAvailable()).isEqualTo(amountAvailable);
        assertThat(computer.getProcessor()).isEqualTo(processor);
        assertThat(computer.getRam()).isEqualTo(ram);
        assertThat(computer.getPcCase()).isEqualTo(pcCase);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Given
        String name = "Konkuter dysk tysionc";
        BigDecimal price = new BigDecimal("-100");
        int amountAvailable = 100;
        Processor processor = new Processor(Processor.Producer.INTEL, Processor.Model.CORE_I7_14700K, 4);
        RAM ram = new RAM(8, RAM.Producer.CORSAIR, RAM.Model.FURY_BEAST);
        PCCase pcCase = new PCCase(2048, 1024);

        // When and Then
        assertThatThrownBy(() -> new Computer(name, price, amountAvailable, processor, ram, pcCase))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("price cannot be negative");
    }
}