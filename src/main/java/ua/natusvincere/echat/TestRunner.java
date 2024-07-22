package ua.natusvincere.echat;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.chat.ChatRepository;
import ua.natusvincere.echat.message.Message;
import ua.natusvincere.echat.message.MessageRepository;
import ua.natusvincere.echat.user.Status;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

@Component
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .status(Status.OFFLINE)
                .firstname("Test")
                .lastname("Test")
                .password("{noop}test")
                .username("tetst")
                .build();
        User user1 = User.builder()
                .email("test1@gmail.com")
                .status(Status.OFFLINE)
                .firstname("Test1")
                .lastname("Test1")
                .password("{noop}test")
                .username("tetst1")
                .build();
        userRepository.save(user);
        userRepository.save(user1);
        Chat chat = Chat.builder()
                .senderId(user.getId())
                .receiverId(user1.getId())
                .build();
        Chat chat1 = Chat.builder()
                .senderId(user1.getId())
                .receiverId(user.getId())
                .build();
        chatRepository.save(chat);
        chatRepository.save(chat1);
        Message message = Message.builder()
                .chatId(chat.getChatId())
                .senderId(user.getId())
                .text("Hello")
                .build();
        Message message1 = Message.builder()
                .chatId(chat1.getChatId())
                .senderId(user1.getId())
                .text("Hello world")
                .build();
        messageRepository.save(message);
        messageRepository.save(message1);
    }
}
