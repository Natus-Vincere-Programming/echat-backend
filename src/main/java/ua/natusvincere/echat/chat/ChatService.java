package ua.natusvincere.echat.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ua.natusvincere.echat.exception.BadRequestException;
import ua.natusvincere.echat.exception.ForbiddenException;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public CreateChatResponse getChatId(CreateChatRequest request, Principal principal,boolean createIfNotExists) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        return repository.findBySenderIdAndReceiverId(user.getId(), request.receiverId())
                .map(chat -> CreateChatResponse.builder()
                        .chatId(chat.getChatId())
                        .build())
                .or(() -> {
                    if (!createIfNotExists){
                        return Optional.empty();
                    }
                    User receiver = userRepository.findById(request.receiverId())
                            .orElseThrow(() -> new BadRequestException("Receiver not found"));
                    UUID chatId = createChat(user.getId(), request.receiverId());
                    Optional<Chat> chat = repository.findByChatIdAndSender(chatId, receiver);
                    chat.ifPresent(this::informCreationChat);
                    return Optional.of(CreateChatResponse.builder()
                            .chatId(chatId)
                            .build());
                }).orElseThrow();
    }

    @SneakyThrows
    private void informCreationChat(Chat chat) {
        messagingTemplate.convertAndSendToUser(
                chat.getSender().getId().toString(), "/queue/chats",
                ChatNotification.builder()
                        .chatId(chat.getChatId())
                        .receiverId(chat.getReceiver().getId())
                        .senderId(chat.getSender().getId())
                        .build()
        );
    }

    private UUID createChat(UUID senderId, UUID receiverId) {
        UUID chatId = UUID.randomUUID();
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new BadRequestException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new BadRequestException("Receiver not found"));
        Chat senderChat = Chat.builder()
                .chatId(chatId)
                .sender(sender)
                .receiver(receiver)
                .build();
        repository.save(senderChat);
        Chat receiverChat = Chat.builder()
                .chatId(chatId)
                .sender(receiver)
                .receiver(sender)
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
                        .senderId(chat.getSender().getId())
                        .receiverId(chat.getReceiver().getId())
                        .build()).toList();
    }

    public ChatResponse getChatByChatId(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        return repository.findByChatIdAndSender(chatId, user)
                .map(chat -> ChatResponse.builder()
                        .chatId(chat.getChatId().toString())
                        .senderId(chat.getSender().getId())
                        .receiverId(chat.getReceiver().getId())
                        .build())
                .orElseThrow(() -> new BadRequestException("Chat not found"));
    }

    @Transactional
    public void deleteChat(UUID chatId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ForbiddenException("User not found"));
        if (!repository.existsByChatIdAndSenderId(chatId, user.getId())) {
            throw new BadRequestException("Chat not found");
        }
        repository.deleteByChatId(chatId);
    }
}
