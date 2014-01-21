package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class HighscorePacket extends Packet {
    private String type;
    private String arg;

    public HighscorePacket(String type, String arg) {
        this.type = type;
        this.arg = arg;
    }

    public String getType() {
        return type;
    }

    public String getArg() {
        return arg;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.type = args.getString(0);
        this.arg = args.getString(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { type, arg };
    }
}
