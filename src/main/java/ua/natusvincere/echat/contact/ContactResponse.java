package ua.natusvincere.echat.contact;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponse {
    private UUID id;
    private UUID contactId;
}
