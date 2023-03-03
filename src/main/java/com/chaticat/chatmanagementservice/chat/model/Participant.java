package com.chaticat.chatmanagementservice.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Participant {

    private UUID id;
    private String username;
}
