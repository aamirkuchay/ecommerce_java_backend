package com.ecommerce.service;

import com.ecommerce.dto.ReviewRequestDTO;
import com.ecommerce.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO addProductReview(ReviewRequestDTO reviewRequestDTO);

    List<ReviewResponseDTO> getProductReviews(Long productId);
}
