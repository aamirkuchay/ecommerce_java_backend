package com.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSKUDto {
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Double weight;
    private Long colorId;
    private List<SKUAttributeDto> attributes;
}
