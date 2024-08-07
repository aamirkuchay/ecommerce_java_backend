package com.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long productSkuId;
    private Integer quantity;
}
