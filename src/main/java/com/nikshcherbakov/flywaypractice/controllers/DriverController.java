package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.services.DriverService;
import com.nikshcherbakov.flywaypractice.services.TruckService;
import com.nikshcherbakov.flywaypractice.utils.SimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;
    private final TruckService truckService;

    @GetMapping("/create/{name}/{truckId}")
    public SimpleResponse createDriver(@PathVariable("name") String name, @PathVariable("truckId") Long truckId) {
        Driver driver = new Driver();
        driver.setName(name);

        if (truckId != null) {
            Truck truck = truckService.findTruckById(truckId);
            if (truck == null) {
                return new SimpleResponse("Error creating driver: no such truck in the database");
            }
            driver.setTruck(truck);
        }

        boolean driverIsSaved = driverService.save(driver);
        return new SimpleResponse(driverIsSaved ? "Driver is saved successfully" : "Driver is not saved!");
    }

    @GetMapping("/get-by-id/{driverId}")
    public Object getTruckById(@PathVariable("driverId") Long driverId) {
        Driver driver = driverService.findDriverById(driverId);
        return driver != null ? driver : new SimpleResponse("No such driver in the database!");
    }

    @GetMapping("/get-all-drivers")
    public Object getAllDrivers() {
        List<Driver> driversFromDb = driverService.findAllDrivers();
        return !driversFromDb.isEmpty() ? driversFromDb :
                new SimpleResponse("There are no drivers in the database yet!");
    }
}
