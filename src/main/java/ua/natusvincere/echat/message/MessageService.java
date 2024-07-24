package ua.natusvincere.echat.message;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.chat.ChatRepository;
import ua.natusvincere.echat.exception.BadRequestException;
import ua.natusvincere.echat.exception.ForbiddenException;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
                .sender(user)
                .text(request.text())
                .createdAt(Instant.now())
                .status(MessageStatus.SENT)
                .build();
        Message savedMessage = messageRepository.save(message);
        // TODO інформувати користувача про нове повідомлення
        /*informChat(savedMessage);*/
        return MessageResponse.builder()
                .id(savedMessage.getId())
                .chatId(savedMessage.getChatId())
                .senderId(savedMessage.getSender().getId())
                .message(savedMessage.getText())
                .status(savedMessage.getStatus())
                .createdAt(savedMessage.getCreatedAt())
                .build();
    }

    @SneakyThrows
    private void informChat(Message savedMessage) {
        User sender = savedMessage.getSender();
        Chat chat = chatRepository
                .findByChatIdAndSender(
                        savedMessage.getChatId(), sender
                ).orElseThrow();
        MessageNotification notification = MessageNotification.builder()
                .createdAt(savedMessage.getCreatedAt())
                .chatId(savedMessage.getChatId())
                .text(savedMessage.getText())
                .senderId(savedMessage.getSender().getId())
                .status(savedMessage.getStatus())
                .build();
        String destination = "/queue/messages";
        messagingTemplate.convertAndSendToUser(
                savedMessage.getSender().getId().toString(),
                destination, notification
        );
        messagingTemplate.convertAndSendToUser(
                chat.getReceiver().getId().toString(),
                destination, notification
        );
    }

    public MessageResponse getLastMessage(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        Chat chat = chatRepository.findByChatIdAndSender(chatId, user)
                .orElseThrow(() -> new BadRequestException("Chat not found"));
        if (!chat.getSender().getId().equals(user.getId()) && !chat.getReceiver().getId().equals(user.getId())) {
            throw new BadRequestException("Chat does not belong to the user");
        }
        return messageRepository.findFirstByChatIdOrderByCreatedAtDesc(chatId)
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .chatId(message.getChatId())
                        .senderId(message.getSender().getId())
                        .message(message.getText())
                        .status(message.getStatus())
                        .createdAt(message.getCreatedAt())
                        .build()
                ).orElseThrow(() -> new BadRequestException("Message not found"));
    }

    public Long getAmountUnreadMessages(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        Chat chat = chatRepository.findByChatIdAndSender(chatId, user)
                .orElseThrow(() -> new BadRequestException("Chat not found"));
        if (!chat.getSender().getId().equals(user.getId()) && !chat.getReceiver().getId().equals(user.getId())) {
            throw new BadRequestException("Chat does not belong to the user");
        }
        Long sent = messageRepository
                .countByChatIdAndSenderIdAndStatus(
                        chatId, chat.getReceiver().getId(), MessageStatus.SENT
                );
        Long delivered = messageRepository
                .countByChatIdAndSenderIdAndStatus(
                        chatId, chat.getReceiver().getId(), MessageStatus.DELIVERED
                );

        return sent + delivered;
    }

    public List<MessageResponse> getMessages(Principal principal, UUID chatId) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        if (!chatRepository.existsByChatIdAndSender(chatId, user)) {
            throw new BadRequestException("Chat not found");
        }
        return messageRepository.findAllByChatId(chatId)
                .stream()
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .chatId(message.getChatId())
                        .senderId(message.getSender().getId())
                        .message(message.getText())
                        .status(message.getStatus())
                        .createdAt(message.getCreatedAt())
                        .build()
                ).toList();
    }
}
