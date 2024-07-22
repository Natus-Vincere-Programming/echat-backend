package ua.natusvincere.echat.message;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.chat.ChatRepository;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;

    public MessageResponse sendMessage(SendMessageRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Message message = Message.builder()
                .chatId(request.chatId())
                .senderId(user.getId())
                .text(request.text())
                .status(MessageStatus.SENT)
                .build();
        Message savedMessage = messageRepository.save(message);
        informChat(savedMessage);
        return MessageResponse.builder()
                .id(savedMessage.getId())
                .chatId(savedMessage.getChatId())
                .senderId(savedMessage.getSenderId())
                .message(savedMessage.getText())
                .status(savedMessage.getStatus())
                .createdAt(savedMessage.getCreatedAt())
                .build();
    }

    @SneakyThrows
    private void informChat(Message savedMessage) {
        Chat chat = chatRepository
                .findByChatIdAndSenderId(
                        savedMessage.getChatId(), savedMessage.getSenderId()
                ).orElseThrow();
        MessageNotification notification = MessageNotification.builder()
                .createdAt(savedMessage.getCreatedAt())
                .chatId(savedMessage.getChatId())
                .text(savedMessage.getText())
                .senderId(savedMessage.getSenderId())
                .status(savedMessage.getStatus())
                .build();
        String destination = "/queue/messages";
        messagingTemplate.convertAndSendToUser(
                savedMessage.getSenderId().toString(),
                destination, notification
        );
        messagingTemplate.convertAndSendToUser(
                chat.getReceiverId().toString(),
                destination, notification
        );
    }
}
