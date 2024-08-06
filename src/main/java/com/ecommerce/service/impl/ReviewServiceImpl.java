package com.ecommerce.service.impl;

import com.ecommerce.dto.ReviewRequestDTO;
import com.ecommerce.dto.ReviewResponseDTO;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRespository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRespository reviewRepository;



    public ReviewResponseDTO addProductReview(ReviewRequestDTO requestDTO) {
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());

        Review savedReview = reviewRepository.save(review);

        return mapToReviewResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getProductReviews(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(this::mapToReviewResponseDTO)
                .collect(Collectors.toList());
    }
















    private ReviewResponseDTO mapToReviewResponseDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setUserId(review.getUser().getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}
