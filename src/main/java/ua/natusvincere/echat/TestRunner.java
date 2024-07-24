package ua.natusvincere.echat;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.chat.ChatRepository;
import ua.natusvincere.echat.message.Message;
import ua.natusvincere.echat.message.MessageRepository;
import ua.natusvincere.echat.message.MessageStatus;
import ua.natusvincere.echat.user.Status;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .status(Status.OFFLINE)
                .firstname("Test")
                .lastname("Test")
                .password(passwordEncoder.encode("test"))
                .username("tetst")
                .build();
        User user1 = User.builder()
                .email("test1@gmail.com")
                .status(Status.OFFLINE)
                .firstname("Test1")
                .lastname("Test1")
                .password(passwordEncoder.encode("test1"))
                .username("tetst1")
                .build();
        userRepository.save(user);
        userRepository.save(user1);
        UUID id = UUID.randomUUID();
        Chat chat = Chat.builder()
                .chatId(id)
                .sender(user)
                .receiver(user1)
                .build();
        Chat chat1 = Chat.builder()
                .chatId(id)
                .sender(user1)
                .receiver(user)
                .build();
        chatRepository.save(chat);
        chatRepository.save(chat1);
        Message message = Message.builder()
                .chatId(chat.getChatId())
                .sender(user)
                .createdAt(Instant.now())
                .status(MessageStatus.DELIVERED)
                .text("Hello")
                .build();
        Message message1 = Message.builder()
                .chatId(chat1.getChatId())
                .sender(user1)
                .createdAt(Instant.now().plusSeconds(360))
                .status(MessageStatus.READ)
                .text("Hello world")
                .build();
        messageRepository.save(message);
        messageRepository.save(message1);
    }
}
