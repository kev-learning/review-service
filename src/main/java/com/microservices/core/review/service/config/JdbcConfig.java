package com.microservices.core.review.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class JdbcConfig {

    @Value("${app.threadPoolSize:10}")
    private Integer threadPoolSize;

    @Value("${app.taskQueueSize:100}")
    private Integer taskQueueSize;

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "jdbc-pool");
    }
}
