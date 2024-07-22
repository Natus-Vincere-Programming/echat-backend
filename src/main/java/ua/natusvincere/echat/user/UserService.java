package ua.natusvincere.echat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UUID registerUser(RegisterUserRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .status(Status.OFFLINE)
                .build();
        return repository.save(user).getId();
    }

    public UserResponse getUser(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with id %s not found", userId)
        ));
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .status(user.getStatus())
                .build();
    }

    public UserResponse searchUser(String username) {
        return repository.findByUsername(username)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .status(user.getStatus())
                        .build())
                .orElse(null);
    }

    public UserResponse getUser(String email) {
        return repository.findByEmail(email)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .status(user.getStatus())
                        .build())
                .orElseThrow();
    }
}
