package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje wat verstuurd wordt naar de speler die aan de beurt is.
 * @author Pieter Bos
 */
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
