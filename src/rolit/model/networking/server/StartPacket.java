package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class StartPacket extends Packet {
    private String[] players;

    public StartPacket(String[] players) {
        this.players = players;
    }

    public String[] getPlayers() {
        return players;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.players = args.getMultiString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { players };
    }
}
