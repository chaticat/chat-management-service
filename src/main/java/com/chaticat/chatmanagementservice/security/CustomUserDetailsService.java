package com.chaticat.chatmanagementservice.security;

import com.chaticat.chatmanagementservice.exception.UserNotFoundException;
import com.chaticat.chatmanagementservice.persistence.entity.User;
import com.chaticat.chatmanagementservice.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return UserPrincipal.create(user);
    }

}
