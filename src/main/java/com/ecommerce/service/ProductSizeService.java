package com.ecommerce.service;

import com.ecommerce.entity.ProductSize;

import java.util.List;

public interface ProductSizeService {

    ProductSize createProductSize(ProductSize productSize);
    ProductSize updateProductSize(Long id, ProductSize productSize);
    void deleteProductSize(Long id);
    ProductSize getProductSizeById(Long id);
    List<ProductSize> getAllProductSizes();
    List<ProductSize> getProductSizesByProductId(Long productId);
}
