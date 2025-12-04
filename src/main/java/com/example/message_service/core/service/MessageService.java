package com.example.message_service.core.service;

import com.example.message_service.core.model.Message;
import com.example.message_service.db.MessageRepository;
import com.example.message_service.ui.DTO.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final AuthService authService;
    //private final EncounterService encounterService;


    public MessageService(MessageRepository messageRepository, AuthService authService
    ) {
        this.messageRepository = messageRepository;
        //this.encounterService = encounterService;
        this.authService = authService;
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

    public List<UserDto> getAvailableUsers(UserDto current) {

        if (current.role().equals("DOCTOR") || current.role().equals("NURSE")) {
            // hämta alla patients
            return authService.getUsersByRole("PATIENT");
        }

        if (current.role().equals("PATIENT")) {
            // hämta doctors + nurses
            List<UserDto> doctors = authService.getUsersByRole("DOCTOR");
            List<UserDto> nurses = authService.getUsersByRole("NURSE");

            doctors.addAll(nurses);
            return doctors;
        }

        return List.of(); // default tom lista
    }


    public Message sendMessage(String sender, String receiver, String content) {
        Message message = new Message();
        message.setSenderEmail(sender);
        message.setReceiverEmail(receiver);
        message.setMessage(content);
        return messageRepository.save(message);
    }
}


