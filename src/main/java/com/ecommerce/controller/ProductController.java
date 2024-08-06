package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.impl.FileStorageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;




    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestPart("product")  ProductDto productCreateDto,
            @RequestPart("images") List<MultipartFile> images) {
        ProductResponseDto createdProduct = productService.createProduct(productCreateDto, images);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        ProductResponseDto productResponse = productService.getProductById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        Page<ProductResponseDto> products = productService.getAllProducts(pageable, category, brand, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }


}
