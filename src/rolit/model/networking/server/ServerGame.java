package rolit.model.networking.server;

import rolit.model.game.Game;
import rolit.model.networking.common.ProtocolException;

import java.util.LinkedList;

public class ServerGame extends Game {
    private final LinkedList<User> players = new LinkedList<User>();
    private final User creator;
    private Server server;
    private boolean started;
    private boolean aborted;

    public ServerGame(User creator, Server server) throws ProtocolException {
        super(1);
        this.creator = creator;
        this.server = server;
        players.add(creator);
        notifyOfChange();
    }

    public User getCreator() {
        return creator;
    }

    public void notifyOfChange() throws ProtocolException {
        server.notifyOfGameChange(this);
    }

    public void addPlayer(User player) throws ProtocolException {
        players.add(player);
        notifyOfChange();
    }

    public void removePlayer(User player) throws ProtocolException {
        players.remove(player);
        notifyOfChange();
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

    public Server getServer() {
        return server;
    }

    public int getStatus() {
        return isAborted() ? -1 : (isStarted() ? 1 : 0);
    }

    public boolean isAborted() {
        return aborted;
    }

    public void abort() throws ProtocolException {
        this.aborted = true;
        notifyOfChange();
    }
}
