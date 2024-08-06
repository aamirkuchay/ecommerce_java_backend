package com.ecommerce.service;


import com.ecommerce.entity.ProductColor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ColorService {

    ProductColor saveColor(ProductColor color);

    Optional<ProductColor> getColorById(Long id);

    ProductColor updateColor(Long id, ProductColor color);

    void deleteColor(Long id);

    Page<ProductColor> getAllColors(int page);
}

