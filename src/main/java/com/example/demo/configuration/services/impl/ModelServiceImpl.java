package com.example.demo.configuration.services.impl;

import com.example.demo.configuration.repositories.ModelRepository;
import com.example.demo.configuration.services.ModelService;
import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.ModelDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public Model save(ModelDto modelDto) {
        Model model = new Model();
        model.setName(modelDto.getName());
        model.setManufactor(modelDto.getManufactor());
        return modelRepository.save(model);
    }

    @Override
    public Model findById(UUID id) {
        return modelRepository.findById(id).orElseThrow(() -> new IllegalStateException("Entity with" + id + "not found"));
    }

    @Override
    public Model edit(UUID uuid, ModelDto model) {
        return modelRepository.findById(uuid)
                .map(it -> {
                    it.setManufactor(model.getManufactor());
                    it.setName(model.getName());
                    return it;
                }).orElseThrow(() -> new IllegalStateException("Entity with" + uuid + "not found"));
    }

    @Override
    public Collection<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public void delete(UUID uuid) {
        modelRepository.deleteById(uuid);
    }
}
