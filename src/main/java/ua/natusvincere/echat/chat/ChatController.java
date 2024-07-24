package ua.natusvincere.echat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.natusvincere.echat.exception.ForbiddenException;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping()
    public ResponseEntity<CreateChatResponse> createChat(@RequestBody CreateChatRequest request, Principal principal) {
        return ResponseEntity.ok(chatService.getChatId(request, principal, true));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable("chatId") UUID chatId, Principal principal) {
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        return ResponseEntity.ok(chatService.getChatByChatId(chatId, principal));
    }

    @GetMapping()
    public ResponseEntity<List<ChatResponse>> findAllChat(Principal principal) {
        return ResponseEntity.ok(chatService.findAllChat(principal.getName()));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable("chatId") UUID chatId, Principal principal) {
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        chatService.deleteChat(chatId, principal);
        return ResponseEntity.noContent().build();
    }
}
