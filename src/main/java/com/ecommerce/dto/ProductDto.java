package com.ecommerce.dto;

import com.ecommerce.entity.ProductStatus;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;



@Data
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private ProductStatus status;
    private List<Long> categoryIds;
    private Long brandId;
    private List<ProductSKUDto> skus;






}
