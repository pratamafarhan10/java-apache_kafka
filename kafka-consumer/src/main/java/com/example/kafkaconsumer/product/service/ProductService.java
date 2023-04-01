package com.example.kafkaconsumer.product.service;

import com.example.kafkaconsumer.repositories.product.ProductRepositoryService;
import com.example.kafkaconsumer.repositories.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepositoryService repositoryService;

    @Value("${file.upload.dir}product/")
    private String UPLOAD_DIR;

    public List<Product> getProducts() {
        return repositoryService.getProducts();
    }

    public Product getProduct(int id) throws Exception {
        return repositoryService.getProduct(id);
    }

    public void deleteProduct(int id) throws Exception {
        repositoryService.deleteProduct(id);
    }

    public Product saveProduct(Product product) {
        return repositoryService.saveProduct(product);
    }

    public boolean uploadImage(MultipartFile file, int id)  {
        try {
            if (file.isEmpty()){
                throw new Exception("File is empty");
            }

            String ext = file.getOriginalFilename().split("\\.")[1];
            if (!ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("jpeg") && !ext.equalsIgnoreCase("png")){
                throw new Exception("Accepted extension: jpg, jpeg, png");
            }

            // Get product
            Product product = repositoryService.getProduct(id);

            // If there is an image already then delete current image
            if (product.getImage() != null){
                File currImg = new File(UPLOAD_DIR + product.getImage());
                boolean deleteSucceed = currImg.delete();

                if (!deleteSucceed){
                    throw new Exception("Failed to delete current image");
                }
            }
            // Else add image
            String uuid = UUID.randomUUID().toString();
            String newFileName = uuid + "." + ext;
            file.transferTo(new File(UPLOAD_DIR + newFileName));

            product.setImage(newFileName);
            repositoryService.saveProduct(product);

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResponseEntity<byte[]> download(String fileName) throws IOException {
        byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIR + fileName).toPath());

        String ext = fileName.split("\\.")[1];
        HttpHeaders headers = new HttpHeaders();

        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")){
            headers.setContentType(MediaType.IMAGE_JPEG);
        }else {
            headers.setContentType(MediaType.IMAGE_PNG);
        }

        return new ResponseEntity<byte[]>(fileData, headers, HttpStatus.OK);
    }
}
