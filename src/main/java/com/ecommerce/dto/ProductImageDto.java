package com.ecommerce.dto;

import lombok.Data;

@Data
public class ProductImageDto {
    private String url;
    private boolean isPrimary;

    public ProductImageDto(String url, boolean primary) {
    }
}
