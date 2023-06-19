package com.chaticat.chatmanagementservice.elasticsearch;

import com.chaticat.chatmanagementservice.chat.model.ChatSearchDto;
import com.chaticat.chatmanagementservice.chat.model.Participant;
import com.chaticat.chatmanagementservice.persistence.entity.Chat;
import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static com.chaticat.chatmanagementservice.elasticsearch.ElasticSearchUserService.ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchChatService {

    private final WebClient elasticSearchWebClient;
    private final ObjectMapper objectMapper;

    @CircuitBreaker(name = ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER, fallbackMethod = "searchChatsByName")
    public Flux<ChatSearchDto> searchChatsByName(final String searchText, final boolean global, final UUID participantId) {
        return elasticSearchWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/chats/name")
                        .queryParam("searchText", searchText)
                        .queryParam("global", global)
                        .queryParam("participantId", participantId)
                        .build()
                )
                .retrieve()
                .bodyToFlux(ChatSearchDto.class);
    }

    public Flux<ChatSearchDto> searchChatsByName(Exception exception) {
        log.error("Cannot connect to elastic search: {}", exception.getMessage());
        return Flux.empty();
    }

    @Async
    @SneakyThrows
    @CircuitBreaker(name = ELASTIC_SEARCH_SERVICE_CIRCUIT_BREAKER, fallbackMethod = "indexChat")
    public void indexChat(final Chat chat, final List<User> users) {
        var participants = users.stream()
                .map(user -> Participant
                        .builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .toList();

        var searchDto = ChatSearchDto
                .builder()
                .id(chat.getId())
                .name(chat.getName())
                .isPrivate(chat.getIsPrivate())
                .isGroup(users.size() > 3)
                .participants(participants)
                .build();

        elasticSearchWebClient
                .post()
                .uri("/search/chats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMapper.writeValueAsString(searchDto)))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    public void indexChat(Exception exception) {
        log.error("Failed to index chat with the reason {}", exception.getMessage());
    }

}
