package com.example.kafkaconsumer.vaccine.service;

import com.example.kafkaconsumer.repositories.vaccine.VaccineRepositoryService;
import com.example.kafkaconsumer.repositories.vaccine.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepositoryService repositoryService;

    public Flux<Vaccine> getVaccines() {
        return repositoryService.getVaccines();
    }

    public List<Vaccine> getVaccinesList() {
        return repositoryService.getVaccinesList();
    }
}
