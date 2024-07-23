package ua.natusvincere.echat.session;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionDetails implements Serializable {

    private String location;
    private String accessType;
}
