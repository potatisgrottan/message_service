package com.example.message_service.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Entity
@Data // Fixar Getters, Setters, ToString etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Message {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id",columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "senderEmail", columnDefinition = "VARCHAR(225)")
    private String senderEmail;

    @Column(name = "receiverEmail", columnDefinition = "VARCHAR(225)")
    private String receiverEmail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sentAt")
    private Date sentAt;

    @Column(name = "message")
    private String message;



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

