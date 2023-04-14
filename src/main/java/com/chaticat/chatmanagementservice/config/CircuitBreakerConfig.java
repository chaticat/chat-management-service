package com.chaticat.chatmanagementservice.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

@Configuration
public class CircuitBreakerConfig {

    public static final String ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER = "elasticSearchService";

    @Bean
    public CircuitBreakerConfigCustomizer externalServiceFooCircuitBreakerConfig() {
        return CircuitBreakerConfigCustomizer
                .of(ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER,
                        builder -> builder.slidingWindowSize(10)
                                .slidingWindowType(COUNT_BASED)
                                .waitDurationInOpenState(Duration.ofSeconds(5))
                                .minimumNumberOfCalls(5)
                                .failureRateThreshold(50.0f));
    }
}
