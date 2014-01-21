package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengePacket extends Packet {
    @Override
    protected void readFromArgs(PacketArgs args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Object[] getData() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
