package com.ecommerce.dto;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long userId;

    public CreateOrderRequest(Long userId) {
        this.userId = userId;
    }
}
