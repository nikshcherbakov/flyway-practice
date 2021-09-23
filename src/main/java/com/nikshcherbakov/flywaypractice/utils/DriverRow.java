package com.nikshcherbakov.flywaypractice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DriverRow {
    private Long id;
    private String name;
    private Long truckId;
}
