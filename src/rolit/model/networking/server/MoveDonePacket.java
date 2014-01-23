package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje wat verstuurd wordt als een speler een zet heeft gedaan.
 * @author Pieter Bos
 */
public class MoveDonePacket extends Packet {
    private String user;
    private int x;
    private int y;

    protected MoveDonePacket() {

    }

    public MoveDonePacket(String user, int x, int y) {
        this.user = user;
        this.x = x;
        this.y = y;
    }

    public String getUser() {
        return user;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.x = args.getInt(1);
        this.y = args.getInt(2);
    }

    /**
     * Geeft het type van de argumenten van het pakketje.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de speler, en de coördinaten van de zet.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { user, x, y };
    }
}
