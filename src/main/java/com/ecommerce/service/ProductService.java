package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;

import java.io.IOException;

public interface ProductService {

   Product saveProduct(ProductDto productDto) throws IOException;
}
