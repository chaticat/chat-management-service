package com.chaticat.chatmanagementservice.user.service;

import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.chaticat.chatmanagementservice.persistence.repository.UserRepository;
import com.chaticat.chatmanagementservice.user.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserById(final UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Contact> getAllUserContacts() {
        UUID currentUserId = UUID.fromString("bf2debf2-e1a1-4069-a52b-57b18a2726c7");
        return userRepository.findAllContactsById(currentUserId)
                .stream()
                .map(user -> {
                    var contact = new Contact();
                    contact.setId(user.getId());
                    contact.setUsername(user.getUsername());

                    return contact;
                })
                .toList();
    }
}
