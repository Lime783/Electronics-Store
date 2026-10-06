package com.example.shop.product.computer.components;

import lombok.Getter;

public record RAM(int capacityInGB, Producer producer, Model model) {

    @Getter
    public enum Producer{
        KINGSTON("Kingston"),
        CORSAIR("Corsair");

        private final String name;

        Producer(String name){
            this.name = name;
        }
    }

    @Getter
    public enum Model{
        FURY_BEAST("Fury Beast"),
        VENGEANCE_LPX("Vengeance LPX");

        private final String name;

        Model(String name){
            this.name = name;
        }
    }
}


