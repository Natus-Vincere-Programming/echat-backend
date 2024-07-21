package ua.natusvincere.echat;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.natusvincere.echat.user.Status;
import ua.natusvincere.echat.user.User;
import ua.natusvincere.echat.user.UserRepository;

@Component
@RequiredArgsConstructor
public class TestRunner implements ApplicationRunner {
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .email("test@gmail.com")
                .status(Status.OFFLINE)
                .firstname("Test")
                .lastname("Test")
                .password("test")
                .username("tetst")
                .build();
        userRepository.save(user);
    }
}
