package ua.natusvincere.echat.user;

public record RegisterUserRequest (
        String username,
        String email,
        String firstname,
        String lastname,
        String password
){
}
