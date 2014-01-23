package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje met een chat bericht.
 * @author Pieter Bos
 */
public class MessagePacket extends Packet {
    private String user;
    private String message;

    protected MessagePacket() {

    }

    public MessagePacket(String user, String message) {
        this.user = user;
        this.message = message;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.message = args.getSpacedString(1);
    }

    /**
     * Geeft het type van de argumenten van het pakketje.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met een speler, en een bericht.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { user, message };
    }
}
