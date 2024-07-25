//package com.ecommerce.controller;
//
//import com.ecommerce.entity.ProductSize;
//import com.ecommerce.service.ProductSizeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/productsizes")
//public class ProductSizeController {
//
//    @Autowired
//    private ProductSizeService productSizeService;
//
//    @PostMapping
//    public ResponseEntity<ProductSize> createProductSize(@RequestBody ProductSize productSize) {
//        ProductSize createdProductSize = productSizeService.createProductSize(productSize);
//        return new ResponseEntity<>(createdProductSize, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductSize> updateProductSize(@PathVariable Long id, @RequestBody ProductSize productSize) {
//        ProductSize updatedProductSize = productSizeService.updateProductSize(id, productSize);
//        return new ResponseEntity<>(updatedProductSize, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProductSize(@PathVariable Long id) {
//        productSizeService.deleteProductSize(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductSize> getProductSizeById(@PathVariable Long id) {
//        ProductSize productSize = productSizeService.getProductSizeById(id);
//        return new ResponseEntity<>(productSize, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ProductSize>> getAllProductSizes() {
//        List<ProductSize> productSizes = productSizeService.getAllProductSizes();
//        return new ResponseEntity<>(productSizes, HttpStatus.OK);
//    }
//
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<ProductSize>> getProductSizesByProductId(@PathVariable Long productId) {
//        List<ProductSize> productSizes = productSizeService.getProductSizesByProductId(productId);
//        return new ResponseEntity<>(productSizes, HttpStatus.OK);
//    }
//}
//
