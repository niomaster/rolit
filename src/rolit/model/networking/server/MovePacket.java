package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MovePacket extends Packet {
    public MovePacket() {

    }

    @Override
    protected void readFromArgs(PacketArgs args) {

    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] {  };
    }

    @Override
    protected Object[] getData() {
        return new Object[] {  };
    }
}
