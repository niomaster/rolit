package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class GamePacket extends Packet {
    private String game;
    private int status;
    private int players;

    public GamePacket(String game, int status, int players) {
        this.game = game;
        this.status = status;
        this.players = players;
    }

    public String getGame() {
        return game;
    }

    public int getStatus() {
        return status;
    }

    public int getPlayers() {
        return players;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.game = args.getString(0);
        this.status = args.getInt(1);
        this.players = args.getInt(2);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { game, status, players };
    }
}
