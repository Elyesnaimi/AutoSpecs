package com.example.demo.configuration.services.impl;

import com.example.demo.configuration.repositories.CarRepository;
import com.example.demo.configuration.services.CarService;
import com.example.demo.configuration.services.ModelService;
import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.CarDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelService modelService;

    public CarServiceImpl(CarRepository carRepository, ModelService modelService) {
        this.carRepository = carRepository;
        this.modelService = modelService;
    }

    @Override
    public Car save(CarDto carDto) {
        Car car = new Car();
        car.setMilage(carDto.getMilage());
        car.setMake(car.getMake());
        Model model = new Model();
        model.setName(carDto.getModel().getName());
        model.setManufactor(carDto.getModel().getManufactor());
        return carRepository.save(car);
    }

    @Override
    public Car findById(UUID id) {
        return carRepository.findById(id).orElseThrow(() -> new IllegalStateException("Entity with" + id + "not found"));
    }

    @Override
    public Car edit(UUID uuid, CarDto car) {
        Model model = modelService.findById(car.getModel().getId());
        return carRepository.findById(uuid)
                .map(it -> {
                    it.setMake(car.getMake());
                    it.setModel(model);
                    it.setMilage(car.getMilage());
                    return it;
                }).orElseThrow(() -> new IllegalStateException("Car with" + uuid + "not found"));
    }

    @Override
    public Collection<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public void delete(UUID uuid) {
        carRepository.deleteById(uuid);
    }
}
