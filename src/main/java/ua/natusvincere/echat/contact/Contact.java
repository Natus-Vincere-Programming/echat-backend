package ua.natusvincere.echat.contact;

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
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private User contact;
}
