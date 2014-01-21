package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Capture {
    /**
     * De lengte van de capture.
     */
    private int length;
    /**
     * De richting van een capture, gegeven met een positie die met de x- en y-coördinaten de richting aangeeft.
     */
    private Position direction;

    /**
     * Maakt een niewe capture met een lengte en richting.
     * @param direction de richting van de capture gegeven met een positie met bepaalde coördinaten die de richting
     * aangeven
     * @param length de lengte van de capture
     */
    public Capture(Position direction, int length){
        this.length = length;
        this.direction = direction;
    }

    /**
     * gGeft de lengte terug.
     * @return een integer van de lengte.
     */
    public int getLength(){
        return length;
    }

    /**
     * Geeft de richting terug.
     * @return geeft de richting terug in een positie.
     */
    public Position getDirection(){
        return direction;
    }


}
