package ua.natusvincere.echat.message;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest request, Principal principal) {
        return ResponseEntity.ok(messageService.sendMessage(request, principal));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<MessageResponse>> getMessages(Principal principal, @PathVariable(name = "chatId") UUID chatId){
        return ResponseEntity.ok(messageService.getMessages(principal, chatId));
    }

    @Operation(summary = "Отримує останнє повідомлення з чату")
    @GetMapping("/{chatId}/last")
    public ResponseEntity<MessageResponse> getLastMessage(@PathVariable UUID chatId, Principal principal) {
        return ResponseEntity.ok(messageService.getLastMessage(chatId, principal));
    }

    @Operation(summary = "Отримує к-сть непрочитаних повідомлення з чату")
    @GetMapping("/{chatId}/amount")
    public ResponseEntity<Long> getAmountUnreadMessages(@PathVariable UUID chatId, Principal principal) {
        return ResponseEntity.ok(messageService.getAmountUnreadMessages(chatId, principal));
    }
}
