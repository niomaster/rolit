package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje dat de server ontvangt van de challenger, bevat wie er gechallenged moet worden.
 * @author Pieter Bos
 */
public class ChallengePacket extends Packet {
    private String challenger;
    private String[] others;

    protected ChallengePacket() {

    }

    public ChallengePacket(String challenger, String[] others) {
        this.challenger = challenger;
        this.others = others;
    }

    /**
     * Zet de argumenten van het pakketje in de variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.challenger = args.getString(0);
        this.others = args.getMultiString(1);
    }

    /**
     * Geeft het type van het argument.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de challenger, en de andere challenged.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { challenger, others };
    }
}
