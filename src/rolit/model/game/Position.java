package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Position {

    private int y;
    private int x;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public Position add(Position direction) {
        Position result = new Position(this.getX() + direction.getX(), this.getY() + direction.getY());
        return result;
    }

    public Position runVector(Position position){
        int x = position.getX();
        int y = position.getY();
        return new Position(x,y);
    }
}
