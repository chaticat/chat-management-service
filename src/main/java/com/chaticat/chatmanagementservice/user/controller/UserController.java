package com.chaticat.chatmanagementservice.user.controller;

import com.chaticat.chatmanagementservice.user.model.UserDto;
import com.chaticat.chatmanagementservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/contacts")
    public List<UserDto> getAllUserContacts() {
        return userService.getAllUserContacts();
    }

    @GetMapping("/search/username")
    public Flux<UserDto> searchUsersByUsername(@RequestParam String searchText, @RequestParam boolean global) {
        return userService.searchUsersByUsername(searchText, global);
    }
}
