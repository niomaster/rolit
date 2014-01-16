package rolit.model.networking.server;

public class User {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public boolean isValidAuthentication() {
        return true;
    }

    public String getUsername() {
        return username;
    }
}
