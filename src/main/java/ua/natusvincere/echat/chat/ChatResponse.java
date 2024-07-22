package ua.natusvincere.echat.chat;

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
