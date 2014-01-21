package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Capture {

    private int length;
    private Vector direction;

    public Capture(Vector direction, int length){
        this.length = length;
        this.direction = direction;
    }

    public int getLength(){
        return length;
    }

    public Vector getDirection(){
        return direction;
    }


}
