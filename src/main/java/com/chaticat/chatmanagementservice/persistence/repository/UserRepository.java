package com.chaticat.chatmanagementservice.persistence.repository;

import com.chaticat.chatmanagementservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
            select c
            from User u
                left join u.contacts c
            where u.id = :id
            """)
    List<User> findAllContactsById(@Param("id") UUID id);

    Optional<User> findByUsername(String username);
}
