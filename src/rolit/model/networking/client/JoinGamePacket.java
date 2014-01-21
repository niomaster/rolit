package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class JoinGamePacket extends Packet {

    private String creator;

    public JoinGamePacket(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.creator = args.getString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { creator };
    }
}
