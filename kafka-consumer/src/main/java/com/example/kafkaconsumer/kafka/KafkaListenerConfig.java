package com.example.kafkaconsumer.kafka;

import com.example.kafkaconsumer.repositories.product.ProductRepositoryService;
import com.example.kafkaconsumer.repositories.product.entity.Product;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerConfig {

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @KafkaListener(
            topics = "ecommerce-product",
            groupId = "ecommerce-product-1"
    )
    void listener(String data){
        try{
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            JsonParser parser = mapper.createParser(data);
            Product product =  parser.readValueAs(Product.class);

            this.productRepositoryService.saveProduct(product);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
