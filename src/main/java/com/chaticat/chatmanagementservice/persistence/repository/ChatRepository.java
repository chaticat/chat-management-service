package com.chaticat.chatmanagementservice.persistence.repository;

import com.chaticat.chatmanagementservice.persistence.entity.Chat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @EntityGraph(attributePaths = "participants")
    @Query("select c from Chat c inner join c.participants p on p.id = :userId order by c.id desc")
    List<Chat> findAllChatsForUser(@Param("userId") UUID userId);
}
