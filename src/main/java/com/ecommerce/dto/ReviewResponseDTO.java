package com.ecommerce.dto;

import com.ecommerce.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long id;
    private Long productId;
    private User user;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
