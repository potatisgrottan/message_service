package com.example.message_service.core.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID senderId;
    private UUID receiverId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    private String message;

    public Message() {
        this.sentAt = new Date();
    }

    public Message(UUID senderId, UUID receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = new Date();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getSenderId() { return senderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }

    public UUID getReceiverId() { return receiverId; }
    public void setReceiverId(UUID receiverId) { this.receiverId = receiverId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }



    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}

