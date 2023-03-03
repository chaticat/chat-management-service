package com.chaticat.chatmanagementservice.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatResponse {

    private UUID id;
    private String name;
    private String iconUrl;
    private boolean isGroup;
    private List<Participant> participants = new ArrayList<>();
}
