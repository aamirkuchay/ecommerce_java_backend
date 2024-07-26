package com.ecommerce.dto;

import lombok.Data;


public class WeightDTO {
    private Double weight;
    private Integer quantity;

    public WeightDTO(Double weight, Integer quantity) {
        this.weight = weight;
        this.quantity = quantity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
