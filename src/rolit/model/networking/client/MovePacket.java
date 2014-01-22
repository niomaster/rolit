package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MovePacket extends Packet {
    private int x;
    private int y;

    protected MovePacket() {

    }

    public MovePacket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.x = args.getInt(0);
        this.y = args.getInt(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { x, y };
    }
}
