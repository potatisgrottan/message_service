package com.example.message_service.core.service;

import com.example.message_service.core.model.Message;
import com.example.message_service.db.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    //private final EncounterService encounterService;

    public MessageService(MessageRepository messageRepository, AuthClient authClient
    ) {
        this.messageRepository = messageRepository;
        //this.encounterService = encounterService;
    }

    public List<Message> getMessagesSentBy(String userId) {
        return messageRepository.findAllBySenderId(userId);
    }

    public List<Message> getMessagesReceivedBy(String userId) {
        return messageRepository.findAllByReceiverId(userId);
    }

    public List<Message> getConversation(String userId, String otherUserId) {
        return messageRepository.findConversation(userId, otherUserId);
    }

    public Message sendMessage(String sender, String receiver, String content) {
        Message message = new Message();
        message.setSenderId(sender);
        message.setReceiverId(receiver);
        message.setMessage(content);
        return messageRepository.save(message);
    }
}


