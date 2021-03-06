package com.nikshcherbakov.flywaypractice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    private Long id;
    private String name;
    private List<Truck> trucks;
}
