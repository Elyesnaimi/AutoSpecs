package com.example.demo.configuration.services;

import com.example.demo.domain.model.Car;
import com.example.demo.ws.dtos.CarDto;

import java.util.Collection;
import java.util.UUID;

public interface CarService {
    Car save(CarDto car);

    Car findById(UUID id);

    Car edit(UUID uuid, CarDto car);

    Collection<Car> getAll();

    void delete(UUID uuid);
}
