package com.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long id;
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
