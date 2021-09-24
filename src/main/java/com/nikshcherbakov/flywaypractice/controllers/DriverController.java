package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.dto.DriverDto;
import com.nikshcherbakov.flywaypractice.controllers.mappers.DriverMapper;
import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.services.DriverService;
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
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        return new ResponseEntity<>(driverMapper.mapAll(driverService.findAllDrivers()), HttpStatus.OK);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable("driverId") Long driverId) {
        Optional<Driver> driver = driverService.findDriverById(driverId);
        return driver.map(value -> new ResponseEntity<>(driverMapper.map(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
