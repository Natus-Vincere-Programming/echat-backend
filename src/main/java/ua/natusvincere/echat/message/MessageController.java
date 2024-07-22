package ua.natusvincere.echat.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody SendMessageRequest request, Principal principal) {
        return ResponseEntity.ok(messageService.sendMessage(request, principal));
    }
}
