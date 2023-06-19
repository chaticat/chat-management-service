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
@Table(name = "users")
@NoArgsConstructor
public class User extends AbstractVersional {

    @Column(name = "username", nullable = false, length = 40)
    private String username;

    @Column(name = "icon-url")
    private String iconUrl;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "private")
    private Boolean isPrivate = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_contacts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<User> contacts = new ArrayList<>();
}


