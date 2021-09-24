package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.services.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j

@RestController
@RequiredArgsConstructor
@RequestMapping("/trucks")
public class TrucksController {

    private final TruckService truckService;

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        return new ResponseEntity<>(truckService.findAllTrucks(), HttpStatus.OK);
    }

    @GetMapping("/{truckId}")
    public ResponseEntity<Truck> getTruckById(@PathVariable("truckId") Long truckId) {
        Optional<Truck> truck = truckService.findTruckById(truckId);
        return truck.map(value -> new ResponseEntity<>(truck.get(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
