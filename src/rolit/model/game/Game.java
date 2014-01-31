package rolit.model.game;

import java.io.IOException;

/**
 * Start het spel, houdt de spelers en het bord bij.
 * @author Martijn de Bijl
 */
public class Game {
    /**
     * Aantal maximale spelers in het spel.
     */
    private final int players;

    /**
     * Het aantal spelers in het spel.
     */
    private int currentNumberOfPlayer;

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
        currentNumberOfPlayer = 0;
        playerArray = new Player[players];
    }

    public int getNumberOfPlayer(){
        return currentNumberOfPlayer;
    }

    /**
     * Geeft het aantal spelers terug.
     *
     * @return de integer van het aantal spelers.
     */
    public int getNumberOfMaximumPlayers() {
        return players;
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
    public void start() throws IOException {
        currentPlayer = 0;

        Player[] playersInGame = new Player[getNumberOfPlayer()];
        for (int i = 0; i < getNumberOfPlayer(); i++){
            playersInGame[i] = playerArray[i];
        }

        while (!board.gameOver()) {
            playersInGame[currentPlayer].doMove(board);
            currentPlayer = (currentPlayer + 1) % (getNumberOfPlayer());
        }
        System.out.println(board.determineWinners()[0]);
        System.out.println(board.getHighScore());
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
            player.setColor(currentPlayer);
            playerArray[currentPlayer] = player;
            currentPlayer = currentPlayer + 1;
            currentNumberOfPlayer = currentNumberOfPlayer + 1;
        }
    }
}
