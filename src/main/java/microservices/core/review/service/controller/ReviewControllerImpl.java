package microservices.core.review.service.controller;

import lombok.extern.slf4j.Slf4j;
import microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.util.exceptions.InvalidInputException;
import com.microservices.core.util.http.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RestController
public class ReviewControllerImpl implements ReviewController{

    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public ResponseEntity<Object> getReviews(Long productId) {
        if(productId < 1) {
            throw new InvalidInputException("Invalid product ID: %s".formatted(productId));
        }

        if(productId == 999) {
           log.debug("No reviews found for product ID: {}", productId);
           return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(Arrays.asList(
                new ReviewDTO(1L, productId, "Author 1", "Subjet 1", "Content 1", serviceUtil.getAddress()),
                new ReviewDTO(2L, productId, "Author 1", "Subjet 1", "Content 1", serviceUtil.getAddress()),
                new ReviewDTO(3L, productId, "Author 1", "Subjet 1", "Content 1", serviceUtil.getAddress())
        ));
    }
}
