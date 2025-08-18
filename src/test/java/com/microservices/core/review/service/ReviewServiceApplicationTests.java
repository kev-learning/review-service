package com.microservices.core.review.service;

import com.microservices.core.review.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ReviewServiceApplicationTests {

	@MockitoBean
	private ReviewRepository reviewRepository;

	@Test
	void contextLoads() {
	}

}
