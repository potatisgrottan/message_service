package com.example.message_service.core.model;

import jakarta.persistence.*;
import java.util.Date;


@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderEmail;
    private String receiverEmail;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    private String message;

    public Message() {
        this.sentAt = new Date();
    }

    public Message(String senderEmail, String receiverEmail, String message) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.sentAt = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getReceiverEmail() { return receiverEmail; }
    public void setReceiverEmail(String receiverEmail) { this.receiverEmail = receiverEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getSentAt() { return sentAt; }
    public void setSentAt(Date sentAt) { this.sentAt = sentAt; }



    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderEmail=" + senderEmail +
                ", receiverEmail=" + receiverEmail +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}

