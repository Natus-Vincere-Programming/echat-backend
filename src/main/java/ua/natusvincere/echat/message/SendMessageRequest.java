package ua.natusvincere.echat.message;

import java.util.UUID;

public record SendMessageRequest(
        UUID chatId,
        String text
) {
}
