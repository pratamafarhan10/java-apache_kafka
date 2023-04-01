package com.example.kafkaproducer.product;

import com.example.kafkaproducer.product.dto.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/create")
    public boolean createProduct(@RequestBody ProductRequest request) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String reqJson = mapper.writeValueAsString(request);

            kafkaTemplate.send("ecommerce-product", reqJson);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
