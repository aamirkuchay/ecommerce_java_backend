package com.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private Integer quantity;
    private Long sizeId;
    private Long weightId;
}
