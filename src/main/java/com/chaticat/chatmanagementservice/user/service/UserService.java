package com.chaticat.chatmanagementservice.user.service;

import com.chaticat.chatmanagementservice.elasticsearch.ElasticSearchUserService;
import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.chaticat.chatmanagementservice.persistence.repository.UserRepository;
import com.chaticat.chatmanagementservice.user.model.UserDto;
import com.chaticat.chatmanagementservice.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ElasticSearchUserService elasticSearchService;

    @Transactional(readOnly = true)
    public User getUserById(final UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Flux<UserDto> searchUsersByUsername(final String searchText, final boolean global) {
        return elasticSearchService.searchUsersByUsername(searchText, global);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUserContacts() {
        return userRepository.findAllContactsById(SecurityUtil.getCurrentUserId())
                .stream()
                .map(user -> UserDto
                        .builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .toList();
    }
}
