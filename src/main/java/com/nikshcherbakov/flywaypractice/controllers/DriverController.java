package com.nikshcherbakov.flywaypractice.controllers;

import com.nikshcherbakov.flywaypractice.dto.DriverDto;
import com.nikshcherbakov.flywaypractice.dto.SimpleResponseDto;
import com.nikshcherbakov.flywaypractice.services.DriverService;
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
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public Object getAllDrivers() {
        List<DriverDto> driversFromDb = driverService.findAllDrivers();
        return !driversFromDb.isEmpty() ? driversFromDb :
                new SimpleResponseDto("There are no drivers in the database yet!");
    }

    @GetMapping("/{driverId}")
    public Object getTruckById(@PathVariable("driverId") Long driverId) {
        DriverDto driver = driverService.findDriverById(driverId);
        return driver != null ? driver : new SimpleResponseDto("No such driver in the database!");
    }
}
