package com.chaticat.chatmanagementservice.chat.service;

import com.chaticat.chatmanagementservice.chat.model.ChatResponse;
import com.chaticat.chatmanagementservice.chat.model.ChatSearchDto;
import com.chaticat.chatmanagementservice.chat.model.EmptyChatRequest;
import com.chaticat.chatmanagementservice.chat.model.Participant;
import com.chaticat.chatmanagementservice.elasticsearch.ElasticSearchChatService;
import com.chaticat.chatmanagementservice.exception.ResourceNotFoundException;
import com.chaticat.chatmanagementservice.persistence.entity.Chat;
import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.chaticat.chatmanagementservice.persistence.repository.ChatRepository;
import com.chaticat.chatmanagementservice.user.service.UserService;
import com.chaticat.chatmanagementservice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ElasticSearchChatService elasticSearchChatService;

    @Transactional(readOnly = true)
    public ChatResponse getChatById(UUID chatId) {
        return chatRepository.findById(chatId)
                .map(this::mapChatToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "chatId", chatId));
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> getAllChatsForUser(UUID userId) {
        return chatRepository.findAllChatsForUser(userId)
                .stream()
                .map(this::mapChatToResponse)
                .toList();
    }

    private ChatResponse mapChatToResponse(Chat chat) {
        var chatResponse = new ChatResponse();
        chatResponse.setId(chat.getId());
        chatResponse.setName(chat.getName());
        chatResponse.setIconUrl(chat.getIconUrl());
        chatResponse.setPrivate(chat.getIsPrivate());

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
        User currentUser = userService.getUserById(SecurityUtil.getCurrentUserId());

        Chat chat = new Chat();
        chat.setName(endUser.getUsername());
        chat.setIconUrl(endUser.getIconUrl());
        chat.setParticipants(List.of(endUser, currentUser));
        chat.setIsPrivate(true);

        Chat savedChat = chatRepository.save(chat);

        elasticSearchChatService.indexChat(savedChat, chat.getParticipants());

        return mapChatToResponse(savedChat);
    }

    @Transactional
    public ChatResponse createEmptyChat(EmptyChatRequest request) {
        User currentUser = userService.getUserById(SecurityUtil.getCurrentUserId());
        List<User> participants = List.of(currentUser);

        Chat chat = new Chat();
        chat.setName(request.getName());
        chat.setParticipants(participants);
        chat.setIsPrivate(true);

        Chat savedChat = chatRepository.save(chat);

        elasticSearchChatService.indexChat(savedChat, participants);

        return mapChatToResponse(savedChat);
    }

    public Flux<ChatSearchDto> searchByName(String searchText, boolean global, UUID participantId) {
        return elasticSearchChatService.searchChatsByName(searchText, global, participantId);
    }
}
