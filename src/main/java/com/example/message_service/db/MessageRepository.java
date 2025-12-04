package com.example.message_service.db;


import com.example.message_service.core.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverEmail(String receiverEmail);
    List<Message> findAllBySenderEmail(String senderEmail);
    @Query("SELECT m FROM Message m WHERE" +
            "(m.senderEmail = :userEmail AND m.receiverEmail= :otherEmail)" +
            "OR (m.senderEmail = :otherEmail AND m.receiverEmail = :userEmail) ORDER BY m.sentAt")
    List<Message> findConversation(@Param("userEmail") String userEmail, @Param("otherEmail") String otherUserEmail);
}

