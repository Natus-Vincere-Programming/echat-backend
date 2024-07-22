package ua.natusvincere.echat.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(description = "Отримує користувача по id. " +
            "Не потребує авторизації")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(service.getUser(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> registerUser(
            @RequestBody @Valid RegisterUserRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> searchUser(
            @RequestParam String username
    ) {
        return ResponseEntity.ok(service.searchUser(username));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getInfo(Principal principal){
        return ResponseEntity.ok(service.getUser(principal.getName()));
    }
}
