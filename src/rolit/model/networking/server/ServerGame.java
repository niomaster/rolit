package rolit.model.networking.server;

import rolit.model.game.Game;

import java.util.LinkedList;

public class ServerGame extends Game {
    private final LinkedList<User> players = new LinkedList<User>();
    private final User creator;
    private boolean started;

    public ServerGame(User creator) {
        super(1);
        this.creator = creator;
        players.add(creator);
    }

    public User getCreator() {
        return creator;
    }

    public void addPlayer(User player) {
        players.add(player);
    }

    public void removePlayer(User player) {
        players.remove(player);
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isStarted() {
        return started;
    }

    public void start() {
        started = true;
    }

    public boolean isNext(User user) {
        return true;
    }

    public boolean canDoMove(int x, int y) {
        return true;
    }

    public void doMove(int x, int y) {

    }
}
