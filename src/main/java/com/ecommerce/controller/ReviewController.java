package com.ecommerce.controller;

import com.ecommerce.dto.ReviewRequestDTO;
import com.ecommerce.dto.ReviewResponseDTO;
import com.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ReviewResponseDTO> addProductReview(@RequestBody @Valid ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO responseDTO = reviewService.addProductReview(reviewRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getProductReviews(@PathVariable Long productId) {
        List<ReviewResponseDTO> reviews = reviewService.getProductReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
