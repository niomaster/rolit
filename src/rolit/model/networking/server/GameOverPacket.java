package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class GameOverPacket extends Packet {
    private int score;
    private String[] winners;

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

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.score = args.getInt(0);
        this.winners = args.getMultiString(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { score, winners };
    }
}
