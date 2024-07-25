package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

@Autowired
private ProductService productService;

//    @PostMapping
//    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto
//                                                ) throws IOException {
////        productDto.setPhoto(photo);
////        @RequestParam("photo") MultipartFile photo
//        Product savedProduct = productService.saveProduct(productDto);
//        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//    }


    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            Product savedProduct = productService.saveProduct(product, images);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
