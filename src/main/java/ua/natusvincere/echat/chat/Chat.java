package ua.natusvincere.echat.chat;

import jakarta.persistence.*;
import lombok.*;
import ua.natusvincere.echat.user.User;

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
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, name = "sender_id")
    private User sender;
    @JoinColumn(nullable = false, updatable = false, name = "receiver_id")
    @ManyToOne
    private User receiver;
}
