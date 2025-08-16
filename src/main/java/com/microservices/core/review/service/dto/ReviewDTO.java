package com.microservices.core.review.service.dto;

import lombok.Builder;

@Builder
public record ReviewDTO (Long reviewId, Long productId, String author, String subject, String content, String serviceAddress) {

}
