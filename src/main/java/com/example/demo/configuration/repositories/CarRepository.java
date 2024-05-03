package com.example.demo.configuration.repositories;

import com.example.demo.domain.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car,UUID> {

}
