package com.ecommerce.service;


import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    ProductResponseDto createProduct(ProductDto productCreateDto, List<MultipartFile> images);

    ProductResponseDto getProductById(Long id);

    Page<ProductResponseDto> getAllProducts(Pageable pageable, String category, String brand, Double minPrice, Double maxPrice);

    void deleteProduct(Long id);

    Page<ProductResponseDto> getProductsByCategory(String categoryName, Pageable pageable);

    Page<ProductResponseDto> getProductsByBrand(Long brandId, PageRequest pageRequest);

    List<ProductResponseDto> getFeaturedProducts(int limit);

    Page<ProductResponseDto> searchProducts(String query, PageRequest pageRequest);
}
