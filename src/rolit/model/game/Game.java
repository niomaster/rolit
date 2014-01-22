package rolit.model.game;

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
     * Geeft het aantal spelers terug.
     *
     * @return de integer van het aantal spelers.
     */
    public int getPlayers() {
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
    public void start() {
        while (!board.gameOver()) {
            playerArray[currentPlayer].doMove(board);
            currentPlayer = (currentPlayer + 1) % players;
            board.toString();
        }
        if (board.gameOver()) {
            System.out.println(board.determineWinner());
        }
    }
}
