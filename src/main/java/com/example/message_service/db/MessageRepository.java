package com.example.message_service.db;


import com.example.message_service.core.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllByReceiverId(UUID receiverId);
    List<Message> findAllBySenderId(UUID senderId);
    @Query("SELECT m FROM Message m WHERE" +
            "(m.senderId = :userId AND m.receiverId = :otherId)" +
            "OR (m.senderId = :otherId AND m.receiverId = :userId) ORDER BY m.sentAt")
    List<Message> findConversation(@Param("userId") UUID userId, @Param("otherId") UUID otherUserId);
}

