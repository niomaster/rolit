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
    protected ArgumentType[] getArgumentTypes() {
        return new ArgumentType[] { ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { creator };
    }
}
