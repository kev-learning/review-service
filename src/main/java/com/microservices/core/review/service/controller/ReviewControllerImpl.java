package com.microservices.core.review.service.controller;

import com.microservices.core.review.service.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import com.microservices.core.review.service.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
public class ReviewControllerImpl{

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ReviewDTO>> getReviews(@RequestParam(name = "productId", required = true) Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }

    @PostMapping(value = "/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ReviewDTO>> createReviews(@RequestBody List<ReviewDTO> reviewDTOS) {
        List<ReviewDTO> reviewDTOList = Optional.ofNullable(reviewDTOS).orElse(Collections.emptyList())
                .stream().map(reviewService::createReview).toList();

        return new ResponseEntity<>(reviewDTOList, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/review")
    void deleteReviews(@RequestParam(name = "productId", required = true) Long productId) {
        reviewService.deleteReviews(productId);
    }
}
