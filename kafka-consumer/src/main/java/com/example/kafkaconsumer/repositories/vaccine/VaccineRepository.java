package com.example.kafkaconsumer.repositories.vaccine;

import com.example.kafkaconsumer.repositories.vaccine.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {
}
