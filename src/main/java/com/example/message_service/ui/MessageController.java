package com.example.message_service.ui;

import com.example.message_service.core.model.Message;
import com.example.message_service.core.service.AuthClient;
import com.example.message_service.core.service.MessageService;
import com.example.message_service.ui.DTO.MessageDTO;
import com.example.message_service.ui.DTO.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final AuthClient authClient;

    public MessageController(MessageService messageService, AuthClient authClient) {
        this.messageService = messageService;
        this.authClient = authClient;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllForUser(
            @RequestHeader("Authorization") String authHeader) {

        UserDto user = authClient.validateBasicAuth(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<Message> sent = messageService.getMessagesSentBy(user.id());
        List<Message> received = messageService.getMessagesReceivedBy(user.id());
        sent.addAll(received);
        sent.sort(Comparator.comparing(Message::getSentAt));

        return ResponseEntity.ok(sent);
    }

    @GetMapping("/conversation/{otherUserId}")
    public ResponseEntity<?> conversation(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String otherUserId) {

        UserDto user = authClient.validateBasicAuth(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(
                messageService.getConversation(user.id(), otherUserId)
        );
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MessageDTO dto) {

        UserDto user = authClient.validateBasicAuth(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Message saved = messageService.sendMessage(
                user.id(),
                dto.receiverId,
                dto.content
        );

        return ResponseEntity.ok(saved);
    }
}
