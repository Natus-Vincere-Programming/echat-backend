package ua.natusvincere.echat.chatroom;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatNotification {

    private UUID chatId;
    private UUID senderId;
    private UUID receiverId;
}
