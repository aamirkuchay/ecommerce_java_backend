package com.ecommerce.service.impl;


import com.ecommerce.entity.ProductColor;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductColorRepository;
import com.ecommerce.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ProductColorRepository productColorRepository;

    @Override
    public ProductColor saveColor(ProductColor color) {
        return productColorRepository.save(color);
    }

    @Override
    public Optional<ProductColor> getColorById(Long id) {
        return productColorRepository.findById(id);
    }

    @Override
    public ProductColor updateColor(Long id, ProductColor color) {
        ProductColor colors = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Color not found with id " + id));
        colors.setName(color.getName());
        colors.setHexCode(color.getHexCode());
        return productColorRepository.save(color);
    }

    @Override
    public void deleteColor(Long id) {
        ProductColor productColor = productColorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Color not found with id " + id));

        productColorRepository.delete(productColor);
    }

    @Override
    public Page<ProductColor> getAllColors(int page) {
        Pageable pageable = PageRequest.of(page , 10);
        return productColorRepository.findAll(pageable);
    }
}
