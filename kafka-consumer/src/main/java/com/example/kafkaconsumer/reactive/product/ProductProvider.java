package com.example.kafkaconsumer.reactive.product;

import com.example.kafkaconsumer.repositories.product.ProductRepositoryService;
import com.example.kafkaconsumer.repositories.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ProductProvider {
    @Autowired
    private ProductRepositoryService repositoryService;

    public Flux<Product> provideProducts(){
        return repositoryService.getFluxProducts().map(this::priceIncrease);
    }

    public Product priceIncrease(Product product){
        product.setPrice(product.getPrice() + 10000);
        return product;
    }
}
