package com.ecommerce.dto;


import com.ecommerce.entity.ProductSize;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SizeDTO {
    private String size;
    private Integer quantity;
    private List<ProductColorDto> colors;


    public static SizeDTO fromProductSize(ProductSize productSize) {
        SizeDTO dto = new SizeDTO();
        dto.setSize(productSize.getSize());
        dto.setQuantity(productSize.getQuantity());
        dto.setColors(productSize.getColors().stream().map(ProductColorDto::fromProductColor).collect(Collectors.toList()));
        return dto;
    }

}
