package ua.natusvincere.echat.chat;

import java.util.UUID;

public record CreateChatRequest(
        UUID senderId,
        UUID receiverId
) {
}
