package ua.natusvincere.echat.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest (
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank @Size(min = 8) String password
){
}
