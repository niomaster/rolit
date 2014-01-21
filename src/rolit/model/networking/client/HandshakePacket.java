package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class HandshakePacket extends Packet {
    private String name;
    private int supports;
    private String version;

    public HandshakePacket(String name, int supports, String version) {

        this.name = name;
        this.supports = supports;
        this.version = version;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.name = args.getString(0);
        this.supports = args.getInt(1);
        this.version = args.getString(2);
    }

    @Override
    protected ArgumentType[] getArgumentTypes() {
        return new ArgumentType[] { ArgumentType.Integer, ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { name, supports, version };
    }

    public String getName() {
        return name;
    }

    public int getSupports() {
        return supports;
    }

    public String getVersion() {
        return version;
    }
}
