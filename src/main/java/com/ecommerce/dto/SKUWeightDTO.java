package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data @AllArgsConstructor
public class SKUWeightDTO {
    private Long weightId;
    private WeightDTO weight;
    private Integer quantity;

}
