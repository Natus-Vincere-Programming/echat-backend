package ua.natusvincere.echat.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Optional<Chat> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    List<Chat> findAllBySenderId(UUID senderId);

    Optional<Chat> findByChatIdAndSenderId(UUID chatId, UUID senderId);
}
