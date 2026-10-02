package com.example.product.computer;

import com.example.product.Product;
import com.example.product.computer.components.PCCase;
import com.example.product.computer.components.Processor;
import com.example.product.computer.components.RAM;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Computer extends Product {
    private Processor processor;
    private RAM ram;
    private PCCase pcCase;

    public Computer(String name, BigDecimal price, int amountAvailable, Processor processor, RAM ram, PCCase pcCase) {
        super(name, price, amountAvailable);

        Objects.requireNonNull(processor, "processor cannot null");
        Objects.requireNonNull(ram, "ram cannot null");
        Objects.requireNonNull(pcCase, "pcCase cannot null");

        this.processor = processor;
        this.ram = ram;
        this.pcCase = pcCase;
    }
}
