package com.microservices.core.review.service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewController {

    @GetMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getReviews(@RequestParam(name = "productId", required = true) Long productId);
}
