package com.nikshcherbakov.flywaypractice.services;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.repositories.DriverRepository;
import com.nikshcherbakov.flywaypractice.utils.DriverRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;
    private final TruckService truckService;

    public boolean save(Driver driver) {
        return repository.save(driver);
    }

    public Driver findDriverById(Long driverId) {
        DriverRow driverRow = repository.findDriverById(driverId);
        if (driverRow == null) {
            return null;
        }

        return generateDriverFromDriverRow(driverRow);
    }

    public List<Driver> findAllDrivers() {
        List<DriverRow> rows = repository.findAllDrivers();
        List<Driver> drivers = new ArrayList<>(rows.size());
        for (DriverRow driverRow : rows) {
            Driver driver = generateDriverFromDriverRow(driverRow);
            drivers.add(driver);
        }
        return drivers;
    }

    private Driver generateDriverFromDriverRow(DriverRow driverRow) {
        Driver driver = new Driver();
        driver.setId(driverRow.getId());
        driver.setName(driverRow.getName());

        if (driverRow.getTruckId() != null) {
            Truck truck = truckService.findTruckById(driverRow.getTruckId());
            driver.setTruck(truck);
        }
        return driver;
    }

}
