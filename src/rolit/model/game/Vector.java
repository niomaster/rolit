package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Vector {

    private int y;
    private int x;

    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public Vector add(Vector direction) {
        Vector result = new Vector(this.getX() + direction.getX(), this.getY() + direction.getY());
        return result;
    }
}
