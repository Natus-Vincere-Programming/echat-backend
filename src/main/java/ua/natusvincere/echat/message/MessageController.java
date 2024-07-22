package ua.natusvincere.echat.message;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
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
}
