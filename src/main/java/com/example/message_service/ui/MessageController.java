package com.example.message_service.ui;

import com.example.message_service.core.model.Message;
import com.example.message_service.core.service.AuthClient;
import com.example.message_service.core.service.AuthService;
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
    private final AuthService authService;
    private final AuthClient authClient;

    public MessageController(MessageService messageService, AuthService authService, AuthClient authClient) {
        this.messageService = messageService;

        this.authService = authService;
        this.authClient = authClient;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllForUser(
            @RequestHeader("Authorization") String authHeader) {

        UserDto user = authClient.validateBasicAuth(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<Message> sent = messageService.getMessagesSentBy(user.email());
        List<Message> received = messageService.getMessagesReceivedBy(user.email());
        sent.addAll(received);
        sent.sort(Comparator.comparing(Message::getSentAt));

        return ResponseEntity.ok(sent);
    }

    @GetMapping("/users/available-to-message")
    public ResponseEntity<?> getAvailableUsers(
            @RequestHeader("Authorization") String authHeader) {

        UserDto current = authClient.validateBasicAuth(authHeader);
        if (current == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }


        List<UserDto> users = messageService.getAvailableUsers(current);

        return ResponseEntity.ok(users);
    }


    @GetMapping("/conversation/{otherUserEmail}")
    public ResponseEntity<?> conversation(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String otherUserEmail) {

        UserDto user = authClient.validateBasicAuth(authHeader);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(
                messageService.getConversation(user.email(), otherUserEmail)
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
                user.email(),
                dto.receiverEmail,
                dto.content
        );

        return ResponseEntity.ok(saved);
    }
}
