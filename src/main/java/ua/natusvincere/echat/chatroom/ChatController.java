package ua.natusvincere.echat.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{senderId}/{receiverId}")
    public ResponseEntity<UUID> createChat(@PathVariable UUID senderId, @PathVariable UUID receiverId) {
        return ResponseEntity.ok(chatService.getChatId(senderId, receiverId, true));
    }

    @GetMapping()
    public ResponseEntity<List<ChatResponse>> findAllChat(Principal principal) {
        return ResponseEntity.ok(chatService.findAllChat(principal.getName()));
    }
}
