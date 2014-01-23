package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje dat wordt verstuurd als het spel wordt gestart.
 * @author Pieter Bos
 */
public class StartPacket extends Packet {
    private String[] players;

    protected StartPacket() {

    }

    public StartPacket(String[] players) {
        this.players = players;
    }

    public String[] getPlayers() {
        return players;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.players = args.getMultiString(0);
    }

    /**
     * Geeft het type van het argument van het pakketje terug.
     * @return het type pakketje.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }
    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de spelers.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { players };
    }
}
