package com.chaticat.chatmanagementservice.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatSearchDto {

    private UUID id;
    private String name;
    private boolean isGroup;
    private Boolean isPrivate;
    private List<Participant> participants;
}
