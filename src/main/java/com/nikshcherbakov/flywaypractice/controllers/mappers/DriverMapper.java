package com.nikshcherbakov.flywaypractice.controllers.mappers;

import com.nikshcherbakov.flywaypractice.dto.DriverDto;
import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverMapper {

    public DriverDto map(Driver driver) {
        DriverDto dto = new DriverDto();
        dto.setId(driver.getId());
        dto.setName(driver.getName());

        Map<Long, String> driverTrucksMap = new HashMap<>(driver.getTrucks().size());
        for (Truck truck : driver.getTrucks()) {
            driverTrucksMap.put(truck.getId(), truck.getBrand());
        }

        dto.setTrucks(driverTrucksMap);
        return dto;
    }

    public List<DriverDto> mapAll(List<Driver> drivers) {
        List<DriverDto> mappedDrivers = new ArrayList<>();
        drivers.forEach(driver -> mappedDrivers.add(map(driver)));
        return mappedDrivers;
    }
}
