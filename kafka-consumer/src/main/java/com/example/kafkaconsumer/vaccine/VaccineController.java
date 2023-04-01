package com.example.kafkaconsumer.vaccine;

import com.example.kafkaconsumer.repositories.vaccine.entity.Vaccine;
import com.example.kafkaconsumer.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/vaccines")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @GetMapping("")
    public Flux<Vaccine> getVaccines(){
        return vaccineService.getVaccines();
    }

    @GetMapping("/list")
    public List<Vaccine> getVaccinesList(){
        return vaccineService.getVaccinesList();
    }
}
