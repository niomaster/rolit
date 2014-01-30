package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakket dat verstuurd wordt als de status van een speler verandert ten aanzien van het gechallenged kunnen worden.
 * @author Pieter Bos
 */
public class CanBeChallengedPacket extends Packet {
    private String user;
    private boolean canBeChallenged;

    protected CanBeChallengedPacket() {

    }

    public CanBeChallengedPacket(String user, boolean canBeChallenged) {
        this.user = user;
        this.canBeChallenged = canBeChallenged;
    }

    public String getUser() {
        return user;
    }

    public boolean isCanBeChallenged() {
        return canBeChallenged;
    }

    /**
     * Zet de argumenten van het pakket in een variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.canBeChallenged = args.getBool(1);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de speler, en de status van het kunnen gechallenged worden.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { user, canBeChallenged };
    }
}
