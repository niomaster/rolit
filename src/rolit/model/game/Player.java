package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Player {

    private final int color;

    public Player(int nummer) {
        this.color = nummer;
    }


    public int getColor() {
        return color;
    }
}
