package com.example.kafkaconsumer.repositories.vaccine.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vaccines")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "supplier")
    private String supplier;
}
