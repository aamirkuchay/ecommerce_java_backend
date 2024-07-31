package com.ecommerce.dto;

import com.ecommerce.entity.ProductColor;
import lombok.Data;

@Data
public class ProductColorDto {
 private Long id;
    private String color;
    private Integer quantity;



    public static ProductColorDto fromProductColor(ProductColor productColor) {
        ProductColorDto dto = new ProductColorDto();
        dto.setColor(productColor.getColor());
        dto.setQuantity(productColor.getQuantity());
        return dto;
    }
}
