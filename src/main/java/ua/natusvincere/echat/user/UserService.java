package ua.natusvincere.echat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    public void disconnectUser(User user) {
        User storedUser = repository.findById(user.getId()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

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
}
