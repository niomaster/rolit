package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Position {

    /**
     * De y coördinaat van de positie.
     */
    private int y;
    /**
     * De x coördinaat van de positie.
     */
    private int x;

    /**
     * Maakt een nieuwe positie.
     * @param x de x-coördinaat van de positie, oftwel de horizontale plaats van de positie.
     * @param y de y-coördinaat van de positie, oftewel de verticale plaats van de positie.
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Geeft de x coördinaat terug van de positie.
     * @return de integer van de x-coördinaat
     */
    public int getX(){
        return x;
    }

    /**
     * Geeft de y coördinaat terug van de positie.
     * @return de integer van de y-coördinaat.
     */
    public int getY(){
        return y;
    }

    /**
     * Telt twee posities bij elkaar op.
     * @param direction de andere positie die erbij wordt opgeteld.
     * @return een nieuwe positie, met nieuwe coördinaten.
     */
    public Position add(Position direction) {
        Position result = new Position(this.getX() + direction.getX(), this.getY() + direction.getY());
        return result;
    }
}
