package com.nikshcherbakov.flywaypractice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Truck {
    private Long id;
    @NonNull private String brand;
    @NonNull private String model;
}
