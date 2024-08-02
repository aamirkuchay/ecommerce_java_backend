package com.ecommerce.service;


import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    ProductResponseDto createProduct(ProductDto productCreateDto, List<MultipartFile> images);
}
