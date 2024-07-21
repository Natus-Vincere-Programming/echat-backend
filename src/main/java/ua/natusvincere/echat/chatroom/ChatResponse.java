package ua.natusvincere.echat.chatroom;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

    private String chatId;
    private UUID senderId;
    private UUID receiverId;
}
