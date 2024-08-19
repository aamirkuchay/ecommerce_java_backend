package com.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class ProductSKUDto {
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Double weight;
    private List<ProductColorDto> colors;
    private List<SKUSizeDTO> sizes;
    private List<SKUWeightDTO> weights;
    private List<SKUAttributeDto> attributes;


    public List<SKUWeightDTO> getWeights() {
        return weights != null ? weights : Collections.emptyList();
    }

}
