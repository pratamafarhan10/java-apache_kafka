package com.example.kafkaproducer.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private int price;
}
