package ua.natusvincere.echat.message;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String message;
    private MessageStatus status;
    private long createdAt;
}
