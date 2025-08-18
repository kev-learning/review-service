package com.microservices.core.review.service.mapper;

import com.microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.review.service.model.Review;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {

    private static final Long COMMON_ID = 1L;

    ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void mapEntityToDTO() {
        Review review = new Review();
        review.setId(COMMON_ID);
        review.setReviewId(COMMON_ID);
        review.setProductId(COMMON_ID);
        review.setVersion(COMMON_ID.intValue());
        review.setAuthor("AUTHOR");
        review.setContent("CONTENT");
        review.setSubject("SUBJECT");

        ReviewDTO reviewDTO = reviewMapper.entityToDTO(review);

        assertEquals(review.getReviewId(), reviewDTO.reviewId());
        assertEquals(review.getProductId(), reviewDTO.productId());
        assertEquals(review.getAuthor(), reviewDTO.author());
        assertEquals(review.getSubject(), reviewDTO.subject());
        assertEquals(review.getContent(), reviewDTO.content());
        assertNull(reviewDTO.serviceAddress());
    }

    @Test
    void mapDTOToEntity() {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(COMMON_ID)
                .productId(COMMON_ID)
                .author("AUTHOR")
                .content("CONTENT")
                .subject("SUBJECT")
                .serviceAddress("ADDRESS")
                .build();

        Review review = reviewMapper.DTOToEntity(reviewDTO);

        assertEquals(reviewDTO.reviewId(), review.getReviewId());
        assertEquals(reviewDTO.productId(), review.getProductId());
        assertEquals(reviewDTO.author(), review.getAuthor());
        assertEquals(reviewDTO.subject(), review.getSubject());
        assertEquals(reviewDTO.content(), review.getContent());
    }
}