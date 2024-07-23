package ua.natusvincere.echat.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public void login(HttpServletRequest request) {
        String username = this.obtainUsername(request);
        username = username != null ? username : "";
        String password = this.obtainPassword(request);
        password = password != null ? password : "";
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        this.authenticationManager.authenticate(authRequest);
    }

    private String obtainPassword(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic ")) {
            String[] parts = new String(Base64.getDecoder().decode(authorization.substring(6))).split(":");
            if (parts.length == 2) {
                return parts[1];
            }
        }
        return null;
    }

    private String obtainUsername(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic ")) {
            String[] parts = new String(Base64.getDecoder().decode(authorization.substring(6))).split(":");
            if (parts.length == 2) {
                return parts[0];
            }
        }
        return null;
    }
}
