package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje wat aangeeft als de game voorbij is.
 * @author Pieter Bos
 */
public class GameOverPacket extends Packet {
    private int score;
    private String[] winners;

    protected GameOverPacket() {

    }

    public GameOverPacket(int score, String[] winners) {
        this.score = score;
        this.winners = winners;
    }

    public int getScore() {
        return score;
    }

    public String[] getWinners() {
        return winners;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.score = args.getInt(0);
        this.winners = args.getMultiString(1);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array van de score en de winnaar(s).
     */
    @Override
    protected Object[] getData() {
        return new Object[] { score, winners };
    }
}
