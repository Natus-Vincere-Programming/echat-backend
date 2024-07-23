package ua.natusvincere.echat.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class UserMixin {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("username")
    String username;

    @JsonProperty("email")
    String email;

    @JsonProperty("firstname")
    String firstname;

    @JsonProperty("lastname")
    String lastname;

    @JsonProperty("password")
    String password;

    @JsonProperty("status")
    Status status;

    @JsonIgnore
    abstract Collection<? extends GrantedAuthority> getAuthorities();
}
