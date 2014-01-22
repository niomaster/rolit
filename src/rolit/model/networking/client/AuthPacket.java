package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje waarmee iemand zich kan autenticeren.
 * @author Pieter Bos
 */
public class AuthPacket extends Packet {
    private String cypherText;

    protected AuthPacket() {

    }

    public AuthPacket(String cypherText) {
        this.cypherText = cypherText;
    }

    public String getCypherText() {
        return cypherText;
    }

    /**
     * Zet de argumenten van het pakket in een variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.cypherText = args.getString(0);
    }

    /**
     * Geeft het type van het argument.
     * @return het argument type: string.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met ce cypherText.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { cypherText };
    }
}
