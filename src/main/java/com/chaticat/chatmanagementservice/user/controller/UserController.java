package com.chaticat.chatmanagementservice.user.controller;

import com.chaticat.chatmanagementservice.user.model.Contact;
import com.chaticat.chatmanagementservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<Contact> getAllUserContacts() {
        return userService.getAllUserContacts();
    }
}
