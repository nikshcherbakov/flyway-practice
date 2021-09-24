package com.nikshcherbakov.flywaypractice.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverTruckDto {
    private Long driverId;
    private Long truckId;
}
