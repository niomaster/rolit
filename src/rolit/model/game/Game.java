package rolit.model.game;

import java.io.IOException;

/**
 * Start het spel, houdt de spelers en het bord bij.
 * @author Martijn de Bijl
 */
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.User;

import java.util.LinkedList;

public class Game {
    /**
     * Aantal spelers in het spel.
     */
    private final int players;

    /**
     * Het bord wat bij het spel hoort.
     */
    private final Board board;

    /**
     * De speler die nu aan de beurt is.
     */
    private int currentPlayer;

    private Player[] playerArray;

    /**
     * Maakt een nieuwe game, met een nieuw bord.
     *
     * @param players het aantal spelers.
     */
    public Game(int players) {
        this.players = players;
        board = new Board();
        currentPlayer = 0;
        playerArray = new Player[players];
    }

    /**
     * Geeft het bord wat bij dit spel hoort.
     *
     * @return het bord.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Geeft de speler die nu aan de beurt is terug.
     *
     * @return de integer die de speler aangeeft.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Start het spel, en laat de spelers spelen, zolang het bord niet vol is.
     */
    public void start() throws ProtocolException {
        while (!board.gameOver()) {
            playerArray[currentPlayer].doMove(board);
            currentPlayer = (currentPlayer + 1) % players;
            System.out.println(board.toString());
        }
        if (board.gameOver()) {
            System.out.println(board.determineWinners());
        }
    }

    /**
     * Voegt de speler toe aan het spel.
     * @param player de speler die toegevoegd wordt.
     */
    public void addPlayer(Player player) throws GameFullException {
        if(currentPlayer >= players){
            throw new GameFullException();
        }
        else {
            playerArray[currentPlayer] = player;
            player.setColor(currentPlayer);
            currentPlayer = currentPlayer + 1;
        }
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players;
    }

    public boolean isLegalMove(int player, Position position) {
        int color = players == 2 ? player * 2 : player;

        return board.isLegalMove(color, position);
    }

    public void doMove(int player, Position position) {
        int color = players == 2 ? player * 2 : player;

        board.doMove(color, position);
    }

    public int getScore(int player) {
        int color = players == 2 ? player * 2 : player;

        return board.getScore(color);
    }

    public int[] determineWinners() {
        Integer[] colors = board.determineWinners();
        int[] players = new int[colors.length];

        int i = 0;
        for(Integer color : colors) {
            players[i] = this.players == 2 ? color / 2 : color;
            i++;
        }

        return players;
    }

    public int getHighScore() {
        return board.getHighScore();
    }
}
