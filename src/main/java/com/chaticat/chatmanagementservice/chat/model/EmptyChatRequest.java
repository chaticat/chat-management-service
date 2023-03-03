package com.chaticat.chatmanagementservice.chat.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptyChatRequest {

    @NotBlank
    private String name;
}
