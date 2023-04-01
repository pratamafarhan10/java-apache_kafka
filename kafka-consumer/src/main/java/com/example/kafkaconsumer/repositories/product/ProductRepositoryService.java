package com.example.kafkaconsumer.repositories.product;

import com.example.kafkaconsumer.repositories.product.entity.Product;
import com.example.kafkaconsumer.exceptionhandler.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductRepositoryService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Transactional
    public Product getProduct(int id) throws NotFoundException {
        Optional<Product> optStudent = repository.findById(id);

        if (optStudent.isPresent()) {
            return optStudent.get();
        }

        throw new NotFoundException("data not found", null);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(int id) throws NotFoundException {
        boolean exist = repository.existsById(id);
        if (exist) {
            repository.deleteById(id);
        }

        throw new NotFoundException("data not found", null);
    }

    public Flux<Product> getFluxProducts(){
        return Flux.fromIterable(repository.findAll());
    }
}
