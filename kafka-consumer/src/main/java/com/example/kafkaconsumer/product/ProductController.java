package com.example.kafkaconsumer.product;

import com.example.kafkaconsumer.product.service.ProductService;
import com.example.kafkaconsumer.repositories.product.entity.Product;
import com.example.kafkaconsumer.responsemapper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @Autowired
    private ResponseMapper responseMapper;

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        return responseMapper.isSuccess(service.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) throws Exception {
        return responseMapper.isSuccess(service.getProduct(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        return responseMapper.isSuccess(service.saveProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws Exception {
        service.deleteProduct(id);
        return responseMapper.isSuccess(null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return responseMapper.isSuccess(service.saveProduct(product));
    }

    @PostMapping("/upload-image/{id}")
    public boolean uploadImage(@RequestParam("image") MultipartFile file, @PathVariable int id) {
        return service.uploadImage(file, id);
    }

    @GetMapping("/download-image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName){
        try {
            return service.download(fileName);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return responseMapper.isNotFound(null);
        }
    }
}
