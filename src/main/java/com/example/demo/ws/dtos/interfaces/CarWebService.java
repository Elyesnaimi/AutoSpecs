package com.example.demo.ws.dtos.interfaces;

import com.example.demo.configuration.services.CarService;
import com.example.demo.configuration.services.impl.CarServiceImpl;
import com.example.demo.domain.model.Car;
import com.example.demo.ws.dtos.CarDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("ws/cars")
public class CarWebService {

    private final CarService carService;

    public CarWebService(@Qualifier("carServiceImpl") CarService carService) {
        this.carService = carService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        Car savedCar = carService.save(carDto);
        CarDto savedCarDto = new CarDto(savedCar);
        return ok(savedCarDto);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<CarDto>> getCars() {
        List<CarDto> cars = carService.getAll()
                .stream()
                .map(CarDto::new)
                .collect(Collectors.toList());
        return ok(cars);
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<CarDto> getCar(@PathVariable UUID id) {
        Car car = carService.findById(id);
        CarDto carDto = new CarDto(car);
        return ok(carDto);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<CarDto> editCar(@PathVariable UUID id, @RequestBody CarDto carDto) {
        Car updatedCar = carService.edit(id, carDto);
        return ok(new CarDto(updatedCar));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.delete(id);
        return noContent().build();
    }
}
