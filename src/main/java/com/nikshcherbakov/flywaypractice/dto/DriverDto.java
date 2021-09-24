package com.nikshcherbakov.flywaypractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverDto {
    private Long id;
    private String name;
    private Map<Long, String> trucks;
}
