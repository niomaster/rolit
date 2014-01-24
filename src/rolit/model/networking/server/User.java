package rolit.model.networking.server;

import rolit.model.game.Player;

/**
 *
 * @author Pieter Bos
 */
public class User {
    private final String username;
    private ClientHandler client;

    public User(String username, ClientHandler client) {
        this.username = username;
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public ClientHandler getClient() {
        return client;
    }

    public void setClient(ClientHandler client) {
        this.client = client;
    }

    public boolean isOnline() {
        return getClient() != null;
    }
}
