package com.example.demo.ws.dtos.interfaces;

import com.example.demo.configuration.services.ModelService;
import com.example.demo.domain.model.Model;
import com.example.demo.ws.dtos.ModelDto;
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
@RequestMapping("ws/models")
public class ModelWebService {

    private final ModelService modelService;

    public ModelWebService(ModelService modelService) {
        this.modelService = modelService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ModelDto> createModel(@RequestBody ModelDto modelDto) {
        Model savedModel = modelService.save(modelDto);
        ModelDto savedModelDto = new ModelDto(savedModel);
        return ResponseEntity.ok(savedModelDto);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<ModelDto>> getModels() {
        List<ModelDto> models = modelService.getAll()
                .stream()
                .map(ModelDto::new)
                .collect(Collectors.toList());
        return ok(models);
    }

    @GetMapping("{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ModelDto> getModel(@PathVariable UUID id) {
        Model model = modelService.findById(id);
        return ResponseEntity.ok(new ModelDto(model));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<ModelDto> editModel(@PathVariable UUID id, @RequestBody ModelDto modelDto) {
        Model updatedModel = modelService.edit(id, modelDto);
        return ok(new ModelDto(updatedModel));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteModel(@PathVariable UUID id) {
        modelService.delete(id);
        return noContent().build();
    }
}
