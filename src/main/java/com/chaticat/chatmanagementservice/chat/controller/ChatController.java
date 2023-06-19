package com.chaticat.chatmanagementservice.chat.controller;

import com.chaticat.chatmanagementservice.chat.model.ChatResponse;
import com.chaticat.chatmanagementservice.chat.model.ChatSearchDto;
import com.chaticat.chatmanagementservice.chat.model.EmptyChatRequest;
import com.chaticat.chatmanagementservice.chat.service.ChatService;
import com.chaticat.chatmanagementservice.security.UserPrincipal;
import com.chaticat.chatmanagementservice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponse> getAllChatsForCurrentUser() {
        return chatService.getAllChatsForUser(SecurityUtil.getCurrentUserId());
    }

    @GetMapping("{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatResponse getChatById(@PathVariable UUID chatId) {
        return chatService.getChatById(chatId);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse createChatWithUser(@PathVariable UUID userId) {
        return chatService.createChatWithUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse createEmptyChat(@RequestBody EmptyChatRequest request) {
        return chatService.createEmptyChat(request);
    }

    @GetMapping("/search/name")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ChatSearchDto> search(@RequestParam String searchText, @RequestParam boolean global, Authentication authentication) {
        UUID currentUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
        return chatService.searchByName(searchText, global, currentUserId);
    }

}

