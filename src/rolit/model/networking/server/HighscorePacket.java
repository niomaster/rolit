package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * @author Pieter Bos
 */
public class HighscorePacket extends Packet {
    private String[] data;

    protected HighscorePacket() {

    }

    public HighscorePacket(String[] data) {
        this.data = data;
    }

    public String[] getDataField() {
        return data;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.data = args.getMultiString(0);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array van de data.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { data };
    }
}
