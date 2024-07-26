package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;

public interface ProductService {
    Product saveProduct(ProductDto productRequest);
//    Product saveProduct(Product product, List<MultipartFile> images) throws IOException;

//   Product saveProduct(ProductDto productDto) throws IOException;
}
