package com.chaticat.chatmanagementservice.persistence.entity;

import com.chaticat.chatmanagementservice.persistence.entity.base.AbstractVersional;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "chat")
@NoArgsConstructor
public class Chat extends AbstractVersional {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "private")
    private Boolean isPrivate = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chats_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();
}
