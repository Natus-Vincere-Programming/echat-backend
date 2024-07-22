package ua.natusvincere.echat.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByStatus(Status status);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
