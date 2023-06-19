package com.chaticat.chatmanagementservice.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Contact {

    private UUID id;
    private String username;
}
