package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengeResponsePacket extends Packet {
    private String user;
    private boolean accept;

    protected ChallengeResponsePacket() {

    }

    public ChallengeResponsePacket(String user, boolean accept) {
        this.user = user;
        this.accept = accept;
    }

    public String getUser() {
        return user;
    }

    public boolean isAccept() {
        return accept;
    }

    /**
     * Zet de argumenten van het pakket in de variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.accept = args.getBool(1);
    }

    /**
     * Geeft het type van de argumenten.
     * @return het type argument: string en boolean.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de speler en de reactie van de speler op de challenge.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { user, accept };
    }
}
