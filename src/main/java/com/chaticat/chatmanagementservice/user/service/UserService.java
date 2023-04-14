package com.chaticat.chatmanagementservice.user.service;

import com.chaticat.chatmanagementservice.elasticsearch.ElasticSearchService;
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
    private final ElasticSearchService elasticSearchService;

    @Transactional(readOnly = true)
    public User getUserById(final UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Flux<UserDto> searchUsersByUsername(final String searchText) {
        return elasticSearchService.searchUsersByUsername(searchText);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUserContacts() {
        return userRepository.findAllContactsById(SecurityUtil.getCurrentUserId())
                .stream()
                .map(user -> {
                    var contact = new UserDto();
                    contact.setId(user.getId());
                    contact.setUsername(user.getUsername());

                    return contact;
                })
                .toList();
    }
}
