package com.microservices.core.review.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@ComponentScan("com.microservices.core")
@EnableJpaRepositories(basePackages = {"com.microservices.core"})
public class ReviewServiceApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		SpringApplication.run(ReviewServiceApplication.class, args);
	}

}
