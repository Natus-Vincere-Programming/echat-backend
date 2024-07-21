package ua.natusvincere.echat.chatroom;

import lombok.*;
import ua.natusvincere.echat.user.UserResponse;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatInformation {
    private UUID chatId;
    private UserResponse sender;
    private UserResponse receiver;
}
