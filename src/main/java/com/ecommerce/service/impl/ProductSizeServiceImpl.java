//package com.ecommerce.service.impl;
//
//
//import com.ecommerce.entity.ProductSize;
//import com.ecommerce.repository.ProductRepository;
//import com.ecommerce.repository.ProductSizeRepository;
//import com.ecommerce.service.ProductSizeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductSizeServiceImpl implements ProductSizeService {
//
//    @Autowired
//    private ProductSizeRepository productSizeRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public ProductSize createProductSize(ProductSize productSize) {
//        return productSizeRepository.save(productSize);
//    }
//
//    @Override
//    public ProductSize updateProductSize(Long id, ProductSize productSize) {
//        ProductSize existingProductSize = productSizeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("ProductSize not found"));
//
//        existingProductSize.setSize(productSize.getSize());
//        existingProductSize.setProduct(productSize.getProduct());
//
//        return productSizeRepository.save(existingProductSize);
//    }
//
//    @Override
//    public void deleteProductSize(Long id) {
//        ProductSize productSize = productSizeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("ProductSize not found"));
//        productSizeRepository.delete(productSize);
//    }
//
//    @Override
//    public ProductSize getProductSizeById(Long id) {
//        return productSizeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("ProductSize not found"));
//    }
//
//    @Override
//    public List<ProductSize> getAllProductSizes() {
//        return productSizeRepository.findAll();
//    }
//
//    @Override
//    public List<ProductSize> getProductSizesByProductId(Long productId) {
////        return productSizeRepository.findByProductId(productId);
//    }
//}
