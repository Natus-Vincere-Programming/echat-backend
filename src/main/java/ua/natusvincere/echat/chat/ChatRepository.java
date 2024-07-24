package ua.natusvincere.echat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.natusvincere.echat.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Optional<Chat> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    List<Chat> findAllBySenderId(UUID senderId);

    Optional<Chat> findByChatIdAndSender(UUID chatId, User sender);

    boolean existsByChatIdAndSenderId(UUID chatId, UUID senderId);

    void deleteByChatId(UUID chatId);

    boolean existsByChatIdAndSender(UUID chatId, User user);
}
