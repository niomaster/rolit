package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MessagePacket extends Packet {
    private String message;

    protected MessagePacket() {

    }

    public MessagePacket(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.message = args.getString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { message };
    }
}
