package com.nikshcherbakov.flywaypractice.services;

import com.nikshcherbakov.flywaypractice.models.Truck;
import com.nikshcherbakov.flywaypractice.repositories.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TruckService {

    private final TruckRepository repository;

    @SuppressWarnings("UnusedReturnValue")
    public Truck save(Truck truck) {
        return repository.save(truck);
    }

    public Optional<Truck> findTruckById(Long truckId) {
        return repository.findTruckById(truckId);
    }

    public List<Truck> findAllTrucks() {
        return repository.findAllTrucks();
    }
}
