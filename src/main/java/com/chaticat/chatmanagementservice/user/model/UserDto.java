package com.chaticat.chatmanagementservice.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID id;
    private String username;
}
