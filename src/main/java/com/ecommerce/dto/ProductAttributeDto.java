package com.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ProductAttributeDto {
    private String name;
    private String value;

    public ProductAttributeDto(String name, String value) {
        this.name = name;
        this.value=value;
    }


}