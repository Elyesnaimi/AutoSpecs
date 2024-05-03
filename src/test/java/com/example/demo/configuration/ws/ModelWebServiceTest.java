package com.example.demo.configuration.ws;

import com.example.demo.configuration.services.impl.ModelServiceImpl;
import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.ModelDto;
import com.example.demo.ws.dtos.interfaces.ModelWebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(ModelWebService.class)
class ModelWebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelServiceImpl modelService;

    private ModelDto modelDto;
    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        model.setId(UUID.randomUUID());
        model.setManufactor("manufactor");
        model.setName("name");

        modelDto = new ModelDto(model.getId(), model.getName(), model.getManufactor());
    }

    @Test
    void test_getAll_shouldReturnListOfModelDtos() throws Exception {
        Mockito.when(modelService.getAll())
                .thenReturn(Collections.singletonList(model));

        mockMvc.perform(MockMvcRequestBuilders.get("/ws/models")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(modelDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(modelDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufactor").value(modelDto.getManufactor()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(modelService).getAll();
    }

    @Test
    void test_getById_shouldReturnModelDto() throws Exception {
        Mockito.when(modelService.findById(modelDto.getId()))
                .thenReturn(model);

        mockMvc.perform(MockMvcRequestBuilders.get("/ws/models/{id}", modelDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(modelDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(modelDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufactor").value(modelDto.getManufactor()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(modelService).findById(model.getId());

    }

    @Test
    void test_deleteById_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(modelService).delete(model.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/ws/models/{id}", modelDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        Mockito.verify(modelService).delete(model.getId());
    }

    @Test
    void test_saveModel_shouldReturnSavedModel() throws Exception {
        Mockito.when(modelService.save(any(ModelDto.class))).thenReturn(model);
        String modelAsString = new ObjectMapper().writeValueAsString(modelDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/ws/models")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(modelAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(modelDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(modelDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufactor").value(modelDto.getManufactor()))
                .andReturn();

        Mockito.verify(modelService).save(any(ModelDto.class));
    }

    @Test
    void test_editModel_shouldReturnEditedModel() throws Exception {
        Mockito.when(modelService.edit(any(UUID.class), any(ModelDto.class))).thenReturn(model);
        String modelAsString = new ObjectMapper().writeValueAsString(modelDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/ws/models/{id}", modelDto.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(modelAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(modelDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(modelDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manufactor").value(modelDto.getManufactor()))
                .andReturn();
        Mockito.verify(modelService).edit(any(UUID.class), any(ModelDto.class));
    }
}
