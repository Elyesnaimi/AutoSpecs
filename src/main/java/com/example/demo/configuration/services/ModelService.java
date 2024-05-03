package com.example.demo.configuration.services;

import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.ModelDto;

import java.util.Collection;
import java.util.UUID;

public interface ModelService {
    Model save(ModelDto model);

    Model findById(UUID id);

    Model edit(UUID uuid, ModelDto model);

    Collection<Model> getAll();

    void delete(UUID uuid);
}
