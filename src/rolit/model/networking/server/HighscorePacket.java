package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class HighscorePacket extends Packet {
    private String[] data;

    public HighscorePacket(String[] data) {
        this.data = data;
    }

    public String[] getDataField() {
        return data;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.data = args.getMultiString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { data };
    }
}
