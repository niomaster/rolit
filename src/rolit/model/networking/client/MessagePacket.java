package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MessagePacket extends Packet {
    @Override
    protected void readFromArgs(PacketArgs args) {
        
    }

    @Override
    protected ArgumentType[] getArgumentTypes() {
        return new ArgumentType[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Object[] getData() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
