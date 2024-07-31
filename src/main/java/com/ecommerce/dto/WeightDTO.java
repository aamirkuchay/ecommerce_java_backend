package com.ecommerce.dto;

import com.ecommerce.entity.ProductWeight;
import lombok.Data;


@Data
public class WeightDTO {
    private Double weight;
    private Integer quantity;



    public static WeightDTO fromProductWeight(ProductWeight productWeight) {
        WeightDTO dto = new WeightDTO();
        dto.setWeight(productWeight.getWeight());
        dto.setQuantity(productWeight.getQuantity());
        return dto;
    }

}
