package com.nikshcherbakov.flywaypractice.services;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.repositories.DriverRepository;
import com.nikshcherbakov.flywaypractice.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    public void save(Driver driver) {
        repository.save(driver);
    }

    public DriverDto findDriverById(Long driverId) {
        Driver driver = repository.findDriverById(driverId);
        return generateDriverDto(driver);
    }

    public List<DriverDto> findAllDrivers() {
        List<Driver> drivers = repository.findAllDrivers();
        List<DriverDto> driverDtoList = new ArrayList<>(drivers.size());
        for (Driver driver : drivers) {
            DriverDto dto = generateDriverDto(driver);
            driverDtoList.add(dto);
        }
        return driverDtoList;
    }

    private DriverDto generateDriverDto(Driver driver) {
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

}
