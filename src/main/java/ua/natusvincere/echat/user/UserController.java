package ua.natusvincere.echat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(service.getUser(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> registerUser(
            @RequestBody RegisterUserRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }
}
