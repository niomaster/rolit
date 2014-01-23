package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje wat verstuurd als de status van een speler verandert.
 * @author Pieter Bos
 */
public class OnlinePacket extends Packet {
    private String user;
    private boolean online;

    protected OnlinePacket() {

    }

    public OnlinePacket(String user, boolean online) {
        this.user = user;
        this.online = online;
    }

    public String getUser() {
        return user;
    }

    public boolean isOnline() {
        return online;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.online = args.getBool(1);
    }

    /**
     * Geeft het type van het argument van het pakketje terug.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array van de naam van de speler, en een boolean of de client online is.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { user, online };
    }
}
