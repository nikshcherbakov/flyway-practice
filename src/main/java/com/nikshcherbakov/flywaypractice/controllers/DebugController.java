package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.dto.SimpleResponseDto;
import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.services.DriverService;
import com.nikshcherbakov.flywaypractice.services.TruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debug")
public class DebugController {

    private final TruckService truckService;
    private final DriverService driverService;

    @PostMapping("/create-driver")
    public SimpleResponseDto createDriver(@RequestParam("name") String name,
                                          @RequestParam(value = "truck_id", required = false) Long[] truckIds) {
        Driver driver = new Driver();
        driver.setName(name);

        if (truckIds != null) {
            List<Truck> driverTrucks = new ArrayList<>(truckIds.length);
            for (Long truckId : truckIds) {
                Truck truck = truckService.findTruckById(truckId);
                if (truck == null) {
                    return new SimpleResponseDto("Error creating driver: no such truck in the database");
                }
                driverTrucks.add(truck);
            }
            driver.setTrucks(driverTrucks);
        }

        driverService.save(driver);
        return new SimpleResponseDto("Driver is saved successfully");
    }

    @PostMapping("/create-truck")
    public SimpleResponseDto createTruck(@RequestParam String brand, @RequestParam String model) {
        Truck truck = new Truck(brand, model);
        truckService.save(truck);
        return new SimpleResponseDto("Truck is saved successfully");
    }
}
