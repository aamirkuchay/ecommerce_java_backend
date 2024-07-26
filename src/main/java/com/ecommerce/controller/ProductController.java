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

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestPart("product") ProductDto productRequest,
                                                 @RequestPart("images") List<MultipartFile> images) {
        productRequest.setImages(images);
        Product savedProduct = productService.saveProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
}
