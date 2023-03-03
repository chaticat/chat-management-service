package com.chaticat.chatmanagementservice.persistence.entity;

import com.chaticat.chatmanagementservice.persistence.entity.base.AbstractVersional;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
}


