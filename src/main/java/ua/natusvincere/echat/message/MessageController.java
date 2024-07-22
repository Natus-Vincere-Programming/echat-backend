package ua.natusvincere.echat.message;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest request, Principal principal) {
        return ResponseEntity.ok(messageService.sendMessage(request, principal));
    }

    @Operation(summary = "Отримує останнє повідомлення з чату")
    @GetMapping("/messages/{chatId}/last")
    public ResponseEntity<MessageResponse> getLastMessage(@PathVariable UUID chatId, Principal principal) {
        return ResponseEntity.ok(messageService.getLastMessage(chatId, principal));
    }

    @Operation(summary = "Отримує к-сть непрочитаних повідомлення з чату")
    @GetMapping("/messages/{chatId}/amount")
    public ResponseEntity<Long> getAmountUnreadMessages(@PathVariable UUID chatId, Principal principal) {
        return ResponseEntity.ok(messageService.getAmountUnreadMessages(chatId, principal));
    }
}
