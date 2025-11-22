package com.example.message_service.core.service;

import com.example.message_service.core.model.Message;
import com.example.message_service.db.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    //private final EncounterService encounterService;

    public MessageService(MessageRepository messageRepository
                        ) {
        this.messageRepository = messageRepository;
        //this.encounterService = encounterService;
    }

    public List<Message> getMessagesSentBy(UUID userId) {
        return messageRepository.findAllBySenderId(userId);
    }

    public List<Message> getMessagesReceivedBy(UUID userId) {
        return messageRepository.findAllByReceiverId(userId);
    }

    public List<Message> getConversation(UUID userId, UUID otherUserId) {
        return messageRepository.findConversation(userId, otherUserId);
    }

    public Message sendMessage(UUID sender, UUID receiver, String content) {
        Message message = new Message();
        message.setSenderId(sender);
        message.setReceiverId(receiver);
        message.setMessage(content);
        return messageRepository.save(message);
    }
}


