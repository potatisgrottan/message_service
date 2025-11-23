package com.example.message_service.core.model;

import jakarta.persistence.*;
import java.util.Date;


@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId;
    private String receiverId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    private String message;

    public Message() {
        this.sentAt = new Date();
    }

    public Message(String senderId, String receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

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

