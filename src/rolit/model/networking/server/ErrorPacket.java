package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ErrorPacket extends Packet {
    private int code;

    public ErrorPacket(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.code = args.getInt(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { this.code };
    }
}
