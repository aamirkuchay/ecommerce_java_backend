package com.ecommerce.dto;

import lombok.Data;

@Data
public class ProductAttributeDto {
    private String name;
    private String value;

    public ProductAttributeDto(String name, String value) {
        this.name = name;
        this.value=value;
    }


}