package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/** Het pakketje dat ervoor zorgt dat een speler een spel joint.
 * @author Pieter Bos
 */
public class JoinGamePacket extends Packet {

    private String creator;

    protected JoinGamePacket() {

    }

    public JoinGamePacket(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.creator = args.getString(0);
    }

    /**
     * Geeft het type van het argument.
     * @return het type van het argument: string.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de creator erin.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { creator };
    }
}
