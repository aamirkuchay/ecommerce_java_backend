package com.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
public class ProductSKUDto {
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Double weight;
    private Long colorId;
    private List<SKUSizeDTO> sizes;
    private List<SKUWeightDTO> weights;
    private List<SKUAttributeDto> attributes;


    public List<SKUWeightDTO> getWeights() {
        return weights != null ? weights : Collections.emptyList();
    }

}
