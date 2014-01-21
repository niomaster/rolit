package rolit.model.game;

/**
 * The player class
 * @author Martijn de Bijl
 */
public abstract class Player {

    private final int color;

    public Player(int nummer) {
        this.color = nummer;
    }

    public int getColor() {
        return color;
    }

    public void doMove(Board board, Position move){
        board.doMove(this, move);
    }


}
