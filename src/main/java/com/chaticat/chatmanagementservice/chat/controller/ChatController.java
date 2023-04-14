package com.chaticat.chatmanagementservice.chat.controller;

import com.chaticat.chatmanagementservice.chat.model.ChatResponse;
import com.chaticat.chatmanagementservice.chat.model.EmptyChatRequest;
import com.chaticat.chatmanagementservice.chat.service.ChatService;
import com.chaticat.chatmanagementservice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<ChatResponse> getAllChatsForCurrentUser() {
        return chatService.getAllChatsForUser(SecurityUtil.getCurrentUserId());
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    private ChatResponse createChatWithUser(@PathVariable UUID userId) {
        return chatService.createChatWithUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ChatResponse createEmptyChat(@RequestBody EmptyChatRequest request) {
        return chatService.createEmptyChat(request);
    }
}

