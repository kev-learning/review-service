package com.microservices.core.review.service.service;

import com.microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.review.service.mapper.ReviewMapper;
import com.microservices.core.review.service.model.Review;
import com.microservices.core.review.service.repository.ReviewRepository;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    private static final Long COMMON_ID = 1L;

    @Mock
    private ServiceUtil serviceUtil;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Spy
    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReviewTest() {
        Mockito.when(reviewMapper.DTOToEntity(buildReviewDTO())).thenReturn(buildReview());
        Mockito.when(reviewMapper.entityToDTO(buildReview())).thenReturn(buildReviewDTO());
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(buildReview());

        ReviewDTO reviewDTO = reviewService.createReview(buildReviewDTO());

        assertNotNull(reviewDTO);
    }

    @Test
    void getReviewsTest() {
        Mockito.when(reviewRepository.findByProductId(Mockito.anyLong())).thenReturn(Collections.singletonList(buildReview()));
        Mockito.when(reviewMapper.entityToDTO(Mockito.any(Review.class))).thenReturn(buildReviewDTO());

        List<ReviewDTO> reviewDTOS = reviewService.getReviews(COMMON_ID);

        assertNotNull(reviewDTOS);
        assertFalse(reviewDTOS.isEmpty());
        assertEquals(1, reviewDTOS.size());
    }

    @Test
    void getReviewsInvalidInputTest() {
        assertThrows(InvalidInputException.class, () -> reviewService.getReviews(0L));
        assertThrows(InvalidInputException.class, () -> reviewService.getReviews(null));
    }

    @Test
    void getReviewsEmptyTest() {
        Mockito.when(reviewRepository.findByProductId(Mockito.anyLong())).thenReturn(Collections.emptyList());

        List<ReviewDTO> reviewDTOS = reviewService.getReviews(COMMON_ID);

        assertNotNull(reviewDTOS);
        assertTrue(reviewDTOS.isEmpty());

        Mockito.verify(reviewMapper, Mockito.never()).entityToDTO(Mockito.any(Review.class));
    }

    @Test
    void deleteReviewsTest() {
        Mockito.when(reviewRepository.findByProductId(Mockito.anyLong())).thenReturn(Collections.singletonList(buildReview()));
        Mockito.doNothing().when(reviewRepository).deleteAll(Mockito.anyList());

        reviewService.deleteReviews(COMMON_ID);

        Mockito.verify(reviewRepository).deleteAll(Mockito.anyList());
    }

    @Test
    void deleteReviewsInvalidInputTest() {
        assertThrows(InvalidInputException.class, () -> reviewService.deleteReviews(0L));
        assertThrows(InvalidInputException.class, () -> reviewService.deleteReviews(null));
    }

    @Test
    void deleteReviewsEmptyTest() {
        Mockito.when(reviewRepository.findByProductId(Mockito.anyLong())).thenReturn(Collections.emptyList());

        reviewService.deleteReviews(COMMON_ID);

        Mockito.verify(reviewRepository, Mockito.never()).deleteAll(Mockito.anyList());
    }

    private Review buildReview() {
        Review review = new Review();
        review.setId(COMMON_ID);
        review.setReviewId(COMMON_ID);
        review.setProductId(COMMON_ID);
        review.setVersion(COMMON_ID.intValue());
        review.setAuthor("AUTHOR");
        review.setContent("CONTENT");
        review.setSubject("SUBJECT");

        return review;
    }

    private ReviewDTO buildReviewDTO() {
        return ReviewDTO.builder()
                .reviewId(COMMON_ID)
                .productId(COMMON_ID)
                .author("AUTHOR")
                .content("CONTENT")
                .subject("SUBJECT")
                .serviceAddress("ADDRESS")
                .build();
    }
}