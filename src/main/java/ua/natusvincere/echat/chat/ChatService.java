package ua.natusvincere.echat.chat;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public UUID getChatId(UUID senderId, UUID receiverId, boolean createIfNotExists) {
        return repository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(Chat::getChatId)
                .or(() -> {
                    if (!createIfNotExists){
                        return Optional.empty();
                    }
                    UUID chatId = createChat(senderId, receiverId);
                    Optional<Chat> chat = repository.findByChatIdAndSenderId(chatId, receiverId);
                    chat.ifPresent(this::informCreationChat);
                    return Optional.of(chatId);
                }).orElseThrow();
    }

    @SneakyThrows
    private void informCreationChat(Chat chat) {
        messagingTemplate.convertAndSendToUser(
                chat.getSenderId().toString(), "/queue/chats",
                ChatNotification.builder()
                        .chatId(chat.getChatId())
                        .receiverId(chat.getReceiverId())
                        .senderId(chat.getSenderId())
                        .build()
        );
    }

    private UUID createChat(UUID senderId, UUID receiverId) {
        UUID chatId = UUID.randomUUID();
        Chat senderChat = Chat.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        repository.save(senderChat);
        Chat receiverChat = Chat.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        repository.save(receiverChat);
        return chatId;
    }

    public List<ChatResponse> findAllChat(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        return repository.findAllBySenderId(user.getId())
                .stream().map(chat -> ChatResponse.builder()
                        .chatId(chat.getChatId().toString())
                        .senderId(chat.getSenderId())
                        .receiverId(chat.getReceiverId())
                        .build()).toList();
    }
}
