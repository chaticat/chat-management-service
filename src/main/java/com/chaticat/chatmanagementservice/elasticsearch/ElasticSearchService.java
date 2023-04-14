package com.chaticat.chatmanagementservice.elasticsearch;

import com.chaticat.chatmanagementservice.user.model.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.chaticat.chatmanagementservice.config.CircuitBreakerConfig.ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final WebClient elasticSearchWebClient;

    @CircuitBreaker(name = ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER, fallbackMethod = "searchUsersByUsernameFallback")
    public Flux<UserDto> searchUsersByUsername(final String searchText) {
        return elasticSearchWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/username")
                        .queryParam("username", searchText)
                        .build()
                )
                .retrieve()
                .bodyToFlux(UserDto.class);
    }

    public Flux<UserDto> searchUsersByUsernameFallback(final String searchText, Exception exception) {
        return Flux.empty();
    }
}
