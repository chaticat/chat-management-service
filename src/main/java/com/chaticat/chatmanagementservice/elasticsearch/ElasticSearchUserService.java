package com.chaticat.chatmanagementservice.elasticsearch;

import com.chaticat.chatmanagementservice.user.model.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchUserService {

    public static final String ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER = "elasticSearchService";

    private final WebClient elasticSearchWebClient;

    @CircuitBreaker(name = ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER, fallbackMethod = "searchUsersByUsername")
    public Flux<UserDto> searchUsersByUsername(final String searchText, final boolean global) {
        return elasticSearchWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/users/username")
                        .queryParam("searchText", searchText)
                        .queryParam("global", global)
                        .build()
                )
                .retrieve()
                .bodyToFlux(UserDto.class);
    }

    public Flux<UserDto> searchUsersByUsername(Exception exception) {
        log.error("Cannot connect to elastic search: {}", exception.getMessage());
        return Flux.empty();
    }
}
