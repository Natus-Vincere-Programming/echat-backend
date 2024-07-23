package ua.natusvincere.echat.message;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import ua.natusvincere.echat.chat.Chat;
import ua.natusvincere.echat.user.User;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID chatId;
    @ManyToOne
    @JoinColumn(name = "sender", nullable = false, updatable = false)
    private User sender;
    @Column(nullable = false, updatable = false)
    private String text;

    @Column(nullable = false)
    private MessageStatus status;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
