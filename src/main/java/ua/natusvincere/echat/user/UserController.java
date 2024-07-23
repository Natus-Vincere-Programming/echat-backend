package ua.natusvincere.echat.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.natusvincere.echat.exception.ForbiddenException;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
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

    @GetMapping("/search")
    public ResponseEntity<UserResponse> searchUser(
            @RequestParam String username
    ) {
        return ResponseEntity.ok(service.searchUser(username));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getInfo(Principal principal){
        if (principal == null) {
            throw new ForbiddenException("You are not authorized");
        }
        return ResponseEntity.ok(service.getUser(principal.getName()));
    }
}
