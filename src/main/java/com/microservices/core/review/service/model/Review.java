package com.microservices.core.review.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews", indexes = { @Index(name = "reviews_unique_index", unique = true, columnList = "productId,reviewId")})
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    private Long reviewId;

    private Long productId;

    private String author;

    private String subject;

    private String content;
}
