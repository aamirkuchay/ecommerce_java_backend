package com.ecommerce.service.impl;

import com.ecommerce.entity.Brand;
import com.ecommerce.entity.Weight;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand saveSize(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Brand brand1 = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight  not found with id " + id));
        brand1.setName(brand.getName());
        brand1.setSlug(brand.getSlug());
        brand1.setLogoUrl(brand.getLogoUrl());
        return brandRepository.save(brand1);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weight  not found with id " + id));
        brandRepository.delete(brand);
    }

    @Override
    public Page<Brand> getAllBrands(int page) {
        Pageable pageable = PageRequest.of(page , 10);
        return brandRepository.findAll(pageable);
    }
}
