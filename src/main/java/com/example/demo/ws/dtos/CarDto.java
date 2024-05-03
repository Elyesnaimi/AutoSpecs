package com.example.demo.ws.dtos;

import com.example.demo.domain.model.Car;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
@AllArgsConstructor
public class CarDto {
    private UUID id;
    private String make;
    private long milage;
    private ModelDto model;


    public CarDto(Car savedCar) {
        id = savedCar.getId();
        make = savedCar.getMake();
        milage = savedCar.getMilage();
        model = new ModelDto(savedCar.getModel());
    }
}
