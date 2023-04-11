package com.chaticat.chatmanagementservice.persistence.repository;

import com.chaticat.chatmanagementservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllContactsById(UUID id);

    Optional<User> findByUsername(String username);
}
