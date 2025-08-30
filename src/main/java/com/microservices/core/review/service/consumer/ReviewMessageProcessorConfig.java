package com.microservices.core.review.service.consumer;

import com.microservices.core.review.service.dto.ReviewDTO;
import com.microservices.core.review.service.service.ReviewService;
import com.microservices.core.util.api.event.Event;
import com.microservices.core.util.exceptions.EventProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;

@Slf4j
@Configuration
public class ReviewMessageProcessorConfig {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    @Qualifier("jdbcScheduler")
    private Scheduler scheduler;

    @Bean
    public Consumer<Event<Long, ReviewDTO>> consumeEvent() {
        return event -> {
            switch (event.getEventType()) {
                case CREATE -> {
                    ReviewDTO reviewDTO = event.getData();
                    log.debug("Creating product: {}", reviewDTO);
                    Mono.fromCallable(() -> Optional.ofNullable(reviewDTO)
                            .map(reviewService::createReview)).log(log.getName(), Level.FINE).subscribeOn(scheduler).block();

                }
                case DELETE -> {
                    Long productId = event.getKey();
                    log.debug("Delete product using ID: {}", productId);
                    Mono.fromRunnable(() -> reviewService.deleteReviews(productId)).subscribeOn(scheduler).then().block();
                }
                default -> {
                    log.warn("Event type not supported: {}", event.getEventType());
                    throw new EventProcessingException("Event type not supported: " + event.getEventType());
                }
            }
        };
    }
}
