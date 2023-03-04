package com.chaticat.chatmanagementservice.chat.service;

import com.chaticat.chatmanagementservice.chat.model.ChatResponse;
import com.chaticat.chatmanagementservice.chat.model.EmptyChatRequest;
import com.chaticat.chatmanagementservice.chat.model.Participant;
import com.chaticat.chatmanagementservice.persistence.entity.Chat;
import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.chaticat.chatmanagementservice.persistence.repository.ChatRepository;
import com.chaticat.chatmanagementservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<ChatResponse> getAllChatsForUser(@PathVariable UUID userId) {
        return chatRepository.findAllChatsForUser(userId)
                .stream()
                .map(ChatService::mapChatToResponse)
                .toList();
    }

    private static ChatResponse mapChatToResponse(Chat chat) {
        var chatResponse = new ChatResponse();
        chatResponse.setId(chat.getId());
        chatResponse.setName(chat.getName());
        chatResponse.setIconUrl(chat.getIconUrl());

        var participants = chat.getParticipants()
                .stream()
                .map(ChatService::mapUserToParticipant)
                .toList();

        chatResponse.setParticipants(participants);
        chatResponse.setGroup(participants.size() < 3);

        return chatResponse;
    }

    private static Participant mapUserToParticipant(User user) {
        var participant = new Participant();
        participant.setId(user.getId());
        participant.setUsername(user.getUsername());

        return participant;
    }

    @Transactional
    public ChatResponse createChatWithUser(UUID userId) {
        User endUser = userService.getUserById(userId);
        UUID currentUserId = UUID.fromString("bf2debf2-e1a1-4069-a52b-57b18a2726c7");
        User currentUser = userService.getUserById(currentUserId);

        Chat chat = new Chat();
        chat.setName(endUser.getUsername());
        chat.setIconUrl(endUser.getIconUrl());
        chat.setParticipants(List.of(endUser, currentUser));

        Chat savedChat = chatRepository.save(chat);

        return mapChatToResponse(savedChat);
    }

    @Transactional
    public ChatResponse createEmptyChat(EmptyChatRequest request) {
        UUID currentUserId = UUID.fromString("bf2debf2-e1a1-4069-a52b-57b18a2726c7");
        User currentUser = userService.getUserById(currentUserId);

        Chat chat = new Chat();
        chat.setName(request.getName());
        chat.setParticipants(List.of(currentUser));

        Chat savedChat = chatRepository.save(chat);

        return mapChatToResponse(savedChat);
    }
}
