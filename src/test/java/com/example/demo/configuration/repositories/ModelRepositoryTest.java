package com.example.demo.configuration.repositories;

import com.example.demo.domain.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DataJpaTest
@EnableJpaAuditing
public class ModelRepositoryTest {

    @Autowired
    private ModelRepository modelRepository;

    @Test
    void test_saveModel_generateUuid() {
        Model model = new Model();
        model.setManufactor("Manufactor");
        model.setName("Name");
        Model savedModel = modelRepository.save(model);

        Assertions.assertNotNull(savedModel.getId());
        Assertions.assertNotNull(savedModel.getCreationDate());
    }
}
