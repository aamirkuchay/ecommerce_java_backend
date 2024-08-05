package com.ecommerce.dto;

import com.ecommerce.entity.ProductAttribute;
import com.ecommerce.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Data
public class ProductDto {
    private String name;
    private String slug;
    private String description;
    private BigDecimal basePrice;
    private ProductStatus status;
    private List<Long> categories; // List of category IDs
    private Long brandId;
    private String metaTitle;
    private String metaDescription;
    private boolean isFeatured;
    private List<ProductAttributeDto> attributes;
    private List<ProductImageDto> images;
    private List<ProductSKUDto> skus;






}
