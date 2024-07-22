package ua.natusvincere.echat.message;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Optional<Message> findFirstByChatIdOrderByCreatedAtDesc(UUID chatId);

    Long countByChatIdAndSenderIdAndStatus(UUID chatId, UUID senderId, MessageStatus status);
}
