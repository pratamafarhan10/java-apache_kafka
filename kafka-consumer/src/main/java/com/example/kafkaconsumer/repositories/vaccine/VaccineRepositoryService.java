package com.example.kafkaconsumer.repositories.vaccine;

import com.example.kafkaconsumer.repositories.vaccine.entity.Vaccine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class VaccineRepositoryService {
    @Autowired
    private VaccineRepository repository;

    public Flux<Vaccine> getVaccines(){
        return Flux.fromIterable(repository.findAll());
    }

    public List<Vaccine> getVaccinesList(){
        return repository.findAll();
    }
}
