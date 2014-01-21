package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class StartGamePacket extends Packet {
    public StartGamePacket() {

    }

    @Override
    protected void readFromArgs(PacketArgs args) {

    }

    @Override
    protected ArgumentType[] getArgumentTypes() {
        return new ArgumentType[] {  };
    }

    @Override
    protected Object[] getData() {
        return new Object[] {  };
    }
}
