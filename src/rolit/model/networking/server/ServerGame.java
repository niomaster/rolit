package rolit.model.networking.server;

import rolit.model.game.Game;
import rolit.model.networking.common.ProtocolException;

import java.util.LinkedList;

/**
 * Het spel waarin gespeeld gaat worden.
 * @author Pieter Bos
 */
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

    public ServerGame(String challenger, LinkedList<String> others, Server server) {
        super(1 + others.size());
        creator = server.getUser(challenger);
        players.add(creator);

        for(String other : others) {
            players.add(server.getUser(other));
        }
    }

    public User getCreator() {
        return creator;
    }

    /**
     * Verstuurd een notificatie als de status van het spel verandert.
     * @throws ProtocolException wordt gegooid als er iets fout gaat.
     */
    public void notifyOfChange() throws ProtocolException {
        server.notifyOfGameChange(this);
    }

    /**
     * Voegt een speler toe aan het spel.
     * @param player de speler die wordt toegevoegd.
     * @throws ProtocolException wordt gegooid als er iets fout gaat.
     */
    public void addPlayer(User player) throws ProtocolException {
        players.add(player);
        notifyOfChange();
    }

    /**
     * Verwijdert een speler uit het spel.
     * @param player de speler die wordt verwijdert.
     * @throws ProtocolException
     */
    public void removePlayer(User player) throws ProtocolException {
        players.remove(player);
        notifyOfChange();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public LinkedList<User> getPlayers() {
        return players;
    }

    public boolean isStarted() {
        return started;
    }

    public void start() throws ProtocolException {
        started = true;

        notifyOfChange();
        server.notifyOfGameStart(this);
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

    /**
     * Breekt het spel af, als er iets mis gaat.
     * @throws ProtocolException wordt gegooid als er iets mis gaat.
     */
    public void abort() throws ProtocolException {
        this.aborted = true;
        notifyOfChange();
    }

    public int getIndex(User user) {
        int index = 0;

        for(User player : players) {
            if(player == user) {
                return index;
            }

            index++;
        }

        return -1;
    }
}
