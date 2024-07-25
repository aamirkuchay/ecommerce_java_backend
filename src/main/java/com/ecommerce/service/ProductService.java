package com.ecommerce.service;

import com.ecommerce.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product, List<MultipartFile> images) throws IOException;

//   Product saveProduct(ProductDto productDto) throws IOException;
}
