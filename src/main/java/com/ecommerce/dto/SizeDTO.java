package com.ecommerce.dto;


import lombok.Data;

import java.util.List;

@Data
public class SizeDTO {
    private String size;
    private Integer quantity;
    private List<ProductColorDto> colors;

}
