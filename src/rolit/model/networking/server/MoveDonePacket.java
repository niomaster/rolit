package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MoveDonePacket extends Packet {
    private String user;
    private int x;
    private int y;

    protected MoveDonePacket() {

    }

    public MoveDonePacket(String user, int x, int y) {
        this.user = user;
        this.x = x;
        this.y = y;
    }

    public String getUser() {
        return user;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.x = args.getInt(1);
        this.y = args.getInt(2);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { user, x, y };
    }
}
