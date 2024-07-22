package ua.natusvincere.echat.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findAllByUserId(UUID userId);

    void deleteByUserIdAndContactId(UUID userId, UUID contactId);
}
