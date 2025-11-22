package com.example.message_service.ui;


import com.example.message_service.core.model.Message;
import com.example.message_service.core.service.MessageService;
import com.example.message_service.ui.DTO.MessageDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all/{userId}")
    public List<Message> getAllForUser(@PathVariable UUID userId) {
        List<Message> sent = messageService.getMessagesSentBy(userId);
        List<Message> received = messageService.getMessagesReceivedBy(userId);
        sent.addAll(received);
        sent.sort(Comparator.comparing(Message::getSentAt));
        return sent;
    }

    @GetMapping("/conversation/{userId}/{otherUserId}")
    public List<Message> conversation(@PathVariable UUID userId,
                                      @PathVariable UUID otherUserId) {
        return messageService.getConversation(userId, otherUserId);
    }

    @PostMapping
    public Message sendMessage(@RequestBody MessageDTO dto) {
        return messageService.sendMessage(dto.senderId, dto.receiverId, dto.content);
    }
}
