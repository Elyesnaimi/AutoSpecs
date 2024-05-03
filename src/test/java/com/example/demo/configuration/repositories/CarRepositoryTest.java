package com.example.demo.configuration.repositories;

import com.example.demo.domain.model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;


    @Test
    void test_saveCar_generateUuid() {
        Car car = new Car();
        car.setMilage(11);
        car.setMake("VW");
        Car savedCar = carRepository.save(car);

        Assertions.assertNotNull(savedCar.getId());
        Assertions.assertNotNull(savedCar.getCreationDate());
    }
}
