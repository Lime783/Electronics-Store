package com.example.product.computer.components;

import lombok.Getter;

public record Processor(Producer producer, Model model, int cores) {

    @Getter
    public enum Producer {
        INTEL("Intel"),
        AMD("AMD");

        private final String producer;

        Producer(String producer) {
            this.producer = producer;
        }
    }

    @Getter
    public enum Model {
        CORE_I5_14600K("Core I5-14600K"),
        CORE_I7_14700K("Core I7-14700K"),
        RYZEN_7_7800X3D("RYZEN-77800X3D"),
        RYZEN_9_7950X("RYZEN-97950X"),;

        private final String model;

        Model(String name) {
            this.model = name;
        }
    }
}
