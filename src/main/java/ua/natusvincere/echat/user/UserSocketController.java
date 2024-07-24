package ua.natusvincere.echat.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserSocketController {

    private final UserRepository userRepository;
    private final UserService userService;

    @MessageMapping("/user/online")
    @SendTo("/user/public")
    public UserResponse online() {
        return userService.setUserOnline("test@gmail.com");
    }
}
