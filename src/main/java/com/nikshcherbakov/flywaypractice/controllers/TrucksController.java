package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.models.Truck;
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
@RequestMapping("/truck")
public class TrucksController {

    private final TruckService truckService;

    @GetMapping("/create/{brand}/{model}")
    public SimpleResponse createTruck(@PathVariable("brand") String brand, @PathVariable("model") String model) {
        Truck truck = new Truck(brand, model);
        boolean truckIsSaved = truckService.save(truck);
        return new SimpleResponse(truckIsSaved ? "Truck is saved successfully" : "Truck is not saved!");
    }

    @GetMapping("/get-by-id/{truckId}")
    public Object getTruckById(@PathVariable("truckId") Long truckId) {
        Truck truck = truckService.findTruckById(truckId);
        return truck != null ? truck : new SimpleResponse("No such truck in the database!");
    }

    @GetMapping("/get-all-trucks")
    public Object getAllTrucks() {
        List<Truck> trucksFromDb = truckService.findAllTrucks();
        return !trucksFromDb.isEmpty() ? trucksFromDb :
                new SimpleResponse("There are no trucks in the database yet!");
    }

}
