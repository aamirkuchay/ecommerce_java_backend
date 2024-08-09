package com.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SKUAttributeDto {
    private String name;
    private String value;

    public SKUAttributeDto(String name, String value) {
        this.name= name;
        this.value = value;
    }
}
