package ua.natusvincere.echat.contact;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddContactRequest(
        @NotNull UUID contactId
) {
}
