package rolit.model.networking.server;

import rolit.model.game.Game;
import rolit.model.game.Position;
import rolit.model.networking.common.ProtocolException;

import java.util.LinkedList;
import java.util.List;

/**
 * Het spel waarin gespeeld gaat worden.
 * @author Pieter Bos
 */
public class ServerGame {
    private final LinkedList<User> players = new LinkedList<User>();
    private final User creator;
    private Server server;
    private boolean started;
    private boolean stopped;

    private Game game;

    public ServerGame(User creator, Server server) throws ProtocolException {
        this.creator = creator;
        this.server = server;
        players.add(creator);
        notifyOfChange();
    }

    public ServerGame(String challenger, LinkedList<String> others, Server server) {
        this.creator = server.getUser(challenger);
        this.server = server;
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

        game = new Game(getPlayerCount());

        notifyOfChange();
        server.notifyOfGameStart(this);
    }

    public Server getServer() {
        return server;
    }

    public int getStatus() {
        return isStopped() ? -1 : (isStarted() ? 1 : 0);
    }

    public boolean isStopped() {
        return stopped;
    }

    /**
     * Breekt het spel af, als er iets mis gaat of als het spel is afgelopen.
     * @throws ProtocolException wordt gegooid als er iets niet goed is in de server.
     */
    public void stop() throws ProtocolException {
        this.stopped = true;
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

    public boolean isLegalMove(int x, int y) {
        return game.isLegalMove(game.getCurrentPlayer(), new Position(x, y));
    }

    public void doMove(int x, int y) {
        game.doMove(game.getCurrentPlayer(), new Position(x, y));
    }

    public void nextPlayer() {
        game.nextPlayer();
    }

    public int getPlayer() {
        return game.getCurrentPlayer();
    }

    public boolean isGameOver() {
        return game.getBoard().gameOver();
    }

    public int getScore() {
        if(!isGameOver()) {
            return 0;
        } else {
            return game.getHighScore();
        }
    }

    public String[] getWinners() {
        int[] winnerIndexes = game.determineWinners();
        List<String> winners = new LinkedList<String>();

        for(Integer index : winnerIndexes) {
            if(index < getPlayers().size()) {
                winners.add(getPlayers().get(index).getUsername());
            }
        }

        String[] winnersArray = new String[winners.size()];
        winners.toArray(winnersArray);
        return winnersArray;
    }
}
