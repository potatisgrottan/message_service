package com.example.message_service.core.service;

import com.example.message_service.core.model.Message;
import com.example.message_service.db.MessageRepository;
import com.example.message_service.ui.DTO.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final AuthClient authClient;

    public MessageService(MessageRepository messageRepository, AuthClient authClient) {
        this.messageRepository = messageRepository;
        this.authClient = authClient;
    }

    public List<Message> getMessagesSentBy(String userEmail) {
        return messageRepository.findAllBySenderEmail(userEmail);
    }

    public List<Message> getMessagesReceivedBy(String userEmail) {
        return messageRepository.findAllByReceiverEmail(userEmail);
    }

    public List<Message> getConversation(String userEmail, String otherUserEmail) {
        return messageRepository.findConversation(userEmail, otherUserEmail);
    }


    public List<UserDto> getAvailableUsers(String myRole, String authHeader) {
        // Enklast att hantera roller versaler
        String role = myRole.toUpperCase();

        if (role.contains("DOCTOR") || role.contains("NURSE")) {
            return authClient.getUsersByRole("PATIENT", authHeader);
        }

        if (role.contains("PATIENT")) {
            List<UserDto> doctors = new ArrayList<>(authClient.getUsersByRole("DOCTOR", authHeader));
            List<UserDto> nurses = authClient.getUsersByRole("NURSE", authHeader);
            doctors.addAll(nurses);
            return doctors;
        }

        return List.of();
    }

    public Message sendMessage(String sender, String receiver, String content) {
        Message message = Message.builder()
                .senderEmail(sender)
                .receiverEmail(receiver)
                .message(content)
                .build();
        return messageRepository.save(message);
    }
}