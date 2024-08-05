package com.ecommerce.service;

import com.ecommerce.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand saveSize(Brand brand);

    Optional<Brand> getBrandById(Long id);

    Brand updateBrand(Long id, Brand brand);

    void deleteBrand(Long id);

    List<Brand> getAllBrands();
}
