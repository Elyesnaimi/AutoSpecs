package com.example.demo.ws.dtos;

import com.example.demo.domain.model.Model;
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
public class ModelDto {
    private UUID id;
    private String name;
    private String manufactor;

    public ModelDto(Model savedModel) {
        id = savedModel.getId();
        name = savedModel.getName();
        manufactor = savedModel.getManufactor();
    }
}
