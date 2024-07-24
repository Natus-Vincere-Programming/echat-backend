package ua.natusvincere.echat.chat;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateChatResponse {
    private UUID chatId;
}
