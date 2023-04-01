package com.example.kafkaconsumer.reactive.vaccine;

import com.example.kafkaconsumer.repositories.vaccine.entity.Vaccine;
import com.example.kafkaconsumer.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class VaccineProvider {

    @Autowired
    private VaccineService service;

    public Flux<Vaccine> provideVaccines(){
        return service.getVaccines().map(this::upperCase);
    }

    public Vaccine upperCase(Vaccine vaccine){
        vaccine.setSupplier(vaccine.getSupplier().toUpperCase());
        return vaccine;
    }
}
