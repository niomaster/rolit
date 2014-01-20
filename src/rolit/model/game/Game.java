package rolit.model.game;

public class Game {

    private final int players;
    private final Board board;
    private int currentPlayer;


    public Game(int players){
        this.players = players;
        board = new Board();
        currentPlayer = 0;
    }

    public int getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean legalMove(Board board){

    }
}
