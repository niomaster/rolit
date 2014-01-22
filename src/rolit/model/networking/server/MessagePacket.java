package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MessagePacket extends Packet {
    private String user;
    private String message;

    protected MessagePacket() {

    }

    public MessagePacket(String user, String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.message = args.getSpacedString(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { user, message };
    }
}
