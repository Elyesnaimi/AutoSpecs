package com.example.demo.configuration.ws;

import com.example.demo.configuration.services.impl.CarServiceImpl;
import com.example.demo.domain.model.Car;
import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.CarDto;
import com.example.demo.ws.dtos.ModelDto;
import com.example.demo.ws.dtos.interfaces.CarWebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarWebService.class)
public class CarWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    private Car car;
    private CarDto carDto;

    private ModelDto modelDto;
    private Model model;


    @BeforeEach
    void setUp() {
        UUID uuid = UUID.randomUUID();
        model = new Model();
        model.setId(uuid);
        model.setName("model name");
        model.setManufactor("model manufactor");

        modelDto = new ModelDto(model);

        car = new Car();
        car.setId(UUID.randomUUID());
        car.setMake("make name");
        car.setModel(model);
        car.setMilage(100);

        carDto = new CarDto(car);
    }

    @Test
    void test_getAll_shouldReturnListOfCarDtos() throws Exception {
        Mockito.when(carService.getAll())
                .thenReturn(Collections.singletonList(car));

        mockMvc.perform(get("/ws/cars")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(car.getId().toString()))
                .andExpect(jsonPath("$[0].model.id").value(car.getModel().getId().toString()))
                .andExpect(jsonPath("$[0].milage").value(car.getMilage()))
                .andExpect(jsonPath("$[0].make").value(car.getMake()))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(carService).getAll();
    }

    @Test
    void test_getById_shouldReturnCarDto() throws Exception {
        Mockito.when(carService.findById(carDto.getId()))
                .thenReturn(car);

        mockMvc.perform(get("/ws/cars/{id}", carDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(car.getId().toString()))
                .andExpect(jsonPath("$.model.id").value(car.getModel().getId().toString()))
                .andExpect(jsonPath("$.milage").value(car.getMilage()))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(carService).findById(car.getId());
    }

    @Test
    void test_deleteById_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(carService).delete(car.getId());

        mockMvc.perform(delete("/ws/cars/{id}", carDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        Mockito.verify(carService).delete(car.getId());
    }

    @Test
    void test_saveCar_shouldReturnSavedModel() throws Exception {
        Mockito.when(carService.save(any(CarDto.class))).thenReturn(car);
        String carAsString = new ObjectMapper().writeValueAsString(carDto);

        mockMvc.perform(post("/ws/cars")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(carAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(car.getId().toString()))
                .andExpect(jsonPath("$.model.id").value(car.getModel().getId().toString()))
                .andExpect(jsonPath("$.milage").value(car.getMilage()))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(carService).save(any(CarDto.class));
    }
}

