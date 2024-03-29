package com.chaticat.chatmanagementservice.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {

    private UUID id;
    private String username;
    private boolean isPrivate;
    private List<Contact> contacts;
}
