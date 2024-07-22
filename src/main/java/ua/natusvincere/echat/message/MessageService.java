package ua.natusvincere.echat.message;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.chat.ChatRepository;
import ua.natusvincere.echat.exception.BadRequestException;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.security.Principal;
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

    public MessageResponse getLastMessage(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Chat chat = chatRepository.findByChatIdAndSenderId(chatId, user.getId())
                .orElseThrow(() -> new BadRequestException("Chat not found"));
        if (!chat.getSenderId().equals(user.getId()) && !chat.getReceiverId().equals(user.getId())) {
            throw new BadRequestException("Chat does not belong to the user");
        }
        return messageRepository.findFirstByChatIdOrderByCreatedAtDesc(chatId)
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .chatId(message.getChatId())
                        .senderId(message.getSenderId())
                        .message(message.getText())
                        .status(message.getStatus())
                        .createdAt(message.getCreatedAt())
                        .build()
                ).orElseThrow(() -> new BadRequestException("Message not found"));
    }

    public Long getAmountUnreadMessages(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Chat chat = chatRepository.findByChatIdAndSenderId(chatId, user.getId())
                .orElseThrow(() -> new BadRequestException("Chat not found"));
        if (!chat.getSenderId().equals(user.getId()) && !chat.getReceiverId().equals(user.getId())) {
            throw new BadRequestException("Chat does not belong to the user");
        }
        Long sent = messageRepository
                .countByChatIdAndSenderIdAndStatus(
                        chatId, chat.getReceiverId(), MessageStatus.SENT
                );
        Long delivered = messageRepository
                .countByChatIdAndSenderIdAndStatus(
                        chatId, chat.getReceiverId(), MessageStatus.DELIVERED
                );

        return sent + delivered;
    }
}
