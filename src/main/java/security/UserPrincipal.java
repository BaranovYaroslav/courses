package security;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private final String login;

    public UserPrincipal(String login) {
        this.login = login;
    }

    @Override
    public String getName() {
        return login;
    }
}
