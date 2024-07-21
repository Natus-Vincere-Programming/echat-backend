package ua.natusvincere.echat.chat;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {

    private String chatId;
    private UUID senderId;
    private UUID recipientId;
    private String content;
}
