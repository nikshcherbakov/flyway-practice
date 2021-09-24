package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.services.TruckService;
import com.nikshcherbakov.flywaypractice.dto.SimpleResponseDto;
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
@RequestMapping("/trucks")
public class TrucksController {

    private final TruckService truckService;

    @GetMapping
    public Object getAllTrucks() {
        List<Truck> trucksFromDb = truckService.findAllTrucks();
        return !trucksFromDb.isEmpty() ? trucksFromDb :
                new SimpleResponseDto("There are no trucks in the database yet!");
    }

    @GetMapping("/{truckId}")
    public Object getTruckById(@PathVariable("truckId") Long truckId) {
        Truck truck = truckService.findTruckById(truckId);
        return truck != null ? truck : new SimpleResponseDto("No such truck in the database!");
    }
}
