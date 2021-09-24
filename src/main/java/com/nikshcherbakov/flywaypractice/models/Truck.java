package com.nikshcherbakov.flywaypractice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Truck {
    private Long id;
    private String brand;
    private String model;

    public Truck(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Truck() {

    }
}
