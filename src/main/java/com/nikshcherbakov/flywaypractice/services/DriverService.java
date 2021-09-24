package com.nikshcherbakov.flywaypractice.services;

import com.nikshcherbakov.flywaypractice.models.Driver;
import com.nikshcherbakov.flywaypractice.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    @SuppressWarnings("UnusedReturnValue")
    public Driver save(Driver driver) {
        return repository.save(driver);
    }

    public Optional<Driver> findDriverById(Long driverId) {
        return repository.findDriverById(driverId);
    }

    public List<Driver> findAllDrivers() {
        return repository.findAllDrivers();
    }

}
