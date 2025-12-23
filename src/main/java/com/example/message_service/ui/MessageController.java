package com.example.message_service.ui;

import com.example.message_service.core.model.Message;
import com.example.message_service.core.service.MessageService;
import com.example.message_service.ui.DTO.MessageDTO;
import com.example.message_service.ui.DTO.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllForUser(@AuthenticationPrincipal Jwt jwt) {
        // Hämta email direkt från token
        String email = jwt.getClaimAsString("email");

        List<Message> sent = messageService.getMessagesSentBy(email);
        List<Message> received = messageService.getMessagesReceivedBy(email);
        sent.addAll(received);
        sent.sort(Comparator.comparing(Message::getSentAt));

        return ResponseEntity.ok(sent);
    }

    @GetMapping("/users/available-to-message")
    public ResponseEntity<List<UserDto>> getAvailableUsers(
            @AuthenticationPrincipal Jwt jwt,
            @RequestHeader("Authorization") String authHeader) {

        // 1. Extrahera roller från Keycloak-token
        // Keycloak lägger ofta roller i: realm_access -> roles
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // Enkel logik: Hitta första rollen som är relevant (DOCTOR, NURSE, PATIENT)
        String myRole = roles.stream()
                .filter(r -> List.of("DOCTOR", "NURSE", "PATIENT").contains(r.toUpperCase()))
                .findFirst()
                .orElse("PATIENT"); // Default fallback

        // 2. Anropa service
        return ResponseEntity.ok(messageService.getAvailableUsers(myRole, authHeader));
    }

    @GetMapping("/conversation/{otherUserEmail}")
    public ResponseEntity<List<Message>> conversation(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String otherUserEmail) {

        String email = jwt.getClaimAsString("email");
        return ResponseEntity.ok(messageService.getConversation(email, otherUserEmail));
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody MessageDTO dto) {

        String email = jwt.getClaimAsString("email");

        Message saved = messageService.sendMessage(
                email,
                dto.receiverEmail,
                dto.content
        );

        return ResponseEntity.ok(saved);
    }
}