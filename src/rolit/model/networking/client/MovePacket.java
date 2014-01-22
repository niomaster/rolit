package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/** Het pakket met de informatie waar de volgende zet gedaan moet worden.
 * @author Pieter Bos
 */

public class MovePacket extends Packet {
    private int x;
    private int y;

    protected MovePacket() {

    }

    public MovePacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.x = args.getInt(0);
        this.y = args.getInt(1);
    }

    /**
     * Geeft het type van het argument.
     * @return het type argument: integer
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een data array van de coördinaten.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { x, y };
    }
}
