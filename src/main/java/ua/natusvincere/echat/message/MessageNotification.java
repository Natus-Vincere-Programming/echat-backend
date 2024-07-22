package ua.natusvincere.echat.message;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageNotification {
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String text;
    private MessageStatus status;
    private Instant createdAt;
}
