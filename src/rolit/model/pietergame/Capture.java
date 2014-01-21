import rolit.common.Position;

public class Capture {

    private final int length;
    private final Position direction;

    public Capture(Position direction, int length) {
        this.length = length;
        this.direction = direction;
    }

    public int getLength() {
        return length;
    }

    public Position getDirection() {
        return direction;
    }

}
