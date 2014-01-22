package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje met de reactie op de challenge.
 * @author Pieter Bos
 */

public class ChallengeResponsePacket extends Packet {
    private boolean response;

    protected ChallengeResponsePacket() {

    }

    public ChallengeResponsePacket(boolean response) {
        this.response = response;
    }

    public boolean getResponse() {
        return response;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @param args de reactie op de challenge.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.response = args.getBool(0);
    }

    /**
     * Geeft het type argument van het pakketje
     * @return het type argument: boolean.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Boolean };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de response van een speler.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { response };
    }
}
