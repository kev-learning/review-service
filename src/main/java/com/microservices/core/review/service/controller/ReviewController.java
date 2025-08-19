package com.microservices.core.review.service.controller;

import com.microservices.core.review.service.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import com.microservices.core.review.service.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.*;
import java.util.logging.Level;

@Slf4j
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    @Qualifier("jdbcScheduler")
    private Scheduler scheduler;

    @GetMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Flux<ReviewDTO>> getReviews(@RequestParam(name = "productId", required = true) Long productId) {

        Flux<ReviewDTO> reviewDTOFlux = Mono.fromCallable(() -> reviewService.getReviews(productId)).flatMapMany(Flux::fromIterable)
                .log(log.getName(), Level.FINE).subscribeOn(scheduler);
        return ResponseEntity.ok(reviewDTOFlux);
    }

    @PostMapping(value = "/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Flux<ReviewDTO>> createReviews(@RequestBody List<ReviewDTO> reviewDTOS) {

        Flux<ReviewDTO> reviewDTOFlux = Mono.fromCallable(() -> Optional.ofNullable(reviewDTOS).orElse(Collections.emptyList())
                .stream().map(reviewService::createReview)).flatMapMany(Flux::fromStream).log(log.getName(), Level.FINE).subscribeOn(scheduler);

        return new ResponseEntity<>(reviewDTOFlux, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/review")
    Mono<Void> deleteReviews(@RequestParam(name = "productId", required = true) Long productId) {
        return Mono.fromRunnable(() -> reviewService.deleteReviews(productId)).subscribeOn(scheduler).then();
    }
}
