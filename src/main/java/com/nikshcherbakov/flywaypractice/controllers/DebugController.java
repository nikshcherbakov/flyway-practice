package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.services.DriverService;
import com.nikshcherbakov.flywaypractice.services.TruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debug")
public class DebugController {

    private final TruckService truckService;
    private final DriverService driverService;

    @PostMapping("/create-driver")
    public ResponseEntity<String> createDriver(@RequestParam("name") String name,
                                       @RequestParam(value = "truck_id", required = false) Long[] truckIds) {
        Driver driver = new Driver();
        driver.setName(name);

        if (truckIds != null) {
            List<Truck> driverTrucks = new ArrayList<>(truckIds.length);
            for (Long truckId : truckIds) {
                Optional<Truck> truck = truckService.findTruckById(truckId);
                if (!truck.isPresent()) return new ResponseEntity<>("Error creating driver: truck is not found!",
                        HttpStatus.NOT_FOUND);
                driverTrucks.add(truck.get());
            }
            driver.setTrucks(driverTrucks);
        }

        driverService.save(driver);
        return new ResponseEntity<>("Driver is saved successfully!", HttpStatus.OK);
    }

    @PostMapping("/create-truck")
    public ResponseEntity<String> createTruck(@RequestParam String brand, @RequestParam String model) {
        Truck truck = new Truck(brand, model);
        truckService.save(truck);
        return new ResponseEntity<>("Truck is saved successfully!", HttpStatus.OK);
    }
}
