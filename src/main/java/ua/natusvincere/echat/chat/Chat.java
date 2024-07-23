package ua.natusvincere.echat.chat;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, updatable = false)
    private UUID chatId;
    @Column(nullable = false, updatable = false)
    private UUID senderId;
    @Column(nullable = false, updatable = false)
    private UUID receiverId;
}
