package com.microservices.core.review.service.service;

import com.microservices.core.review.service.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getReviews(Long productId);

    void deleteReviews(Long productId);
}
