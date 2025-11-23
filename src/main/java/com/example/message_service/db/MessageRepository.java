package com.example.message_service.db;


import com.example.message_service.core.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverId(String receiverId);
    List<Message> findAllBySenderId(String senderId);
    @Query("SELECT m FROM Message m WHERE" +
            "(m.senderId = :userId AND m.receiverId = :otherId)" +
            "OR (m.senderId = :otherId AND m.receiverId = :userId) ORDER BY m.sentAt")
    List<Message> findConversation(@Param("userId") String userId, @Param("otherId") String otherUserId);
}

