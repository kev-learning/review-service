package com.microservices.core.review.service.service;

import com.microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.review.service.mapper.ReviewMapper;
import com.microservices.core.review.service.model.Review;
import com.microservices.core.review.service.repository.ReviewRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = reviewMapper.DTOToEntity(reviewDTO);
        review = reviewRepository.save(review);

        log.debug("Created new review: {}", review);
        return reviewMapper.entityToDTO(review, serviceUtil.getAddress());
    }

    @Override
    public List<ReviewDTO> getReviews(Long productId) {

        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        List<Review> reviews = reviewRepository.findByProductId(productId);

        log.debug("Found reviews: {}", reviews);

        return Optional.ofNullable(reviews).orElse(Collections.emptyList())
                .stream().map(review -> reviewMapper.entityToDTO(review, serviceUtil.getAddress())).toList();
    }

    @Override
    public void deleteReviews(Long productId) {
        if(Objects.isNull(productId) || productId < 1) {
            throw new InvalidInputException("Invalid product ID: " + productId);
        }

        List<Review> reviews = reviewRepository.findByProductId(productId);

        if(!CollectionUtils.isEmpty(reviews)) {
            log.debug("Deleting reviews: {}", reviews);
            reviewRepository.deleteAll(reviews);
        }
    }
}
