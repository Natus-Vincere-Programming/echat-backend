package ua.natusvincere.echat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping()
    public ResponseEntity<UUID> createChat(CreateChatRequest request) {
        return ResponseEntity.ok(chatService.getChatId(request, true));
    }

    @GetMapping()
    public ResponseEntity<List<ChatResponse>> findAllChat(Principal principal) {
        return ResponseEntity.ok(chatService.findAllChat(principal.getName()));
    }
}
