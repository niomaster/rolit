package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class HandshakePacket extends Packet {
    private int supports;
    private String version;
    private String nonce;

    public HandshakePacket(int supports, String version, String nonce) {
        this.supports = supports;
        this.version = version;
        this.nonce = nonce;
    }

    public HandshakePacket(int supports, String version) {
        this(supports, version, null);
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.supports = args.getInt(0);
        this.version = args.getString(1);
        this.nonce = args.getMultiString(2).length == 1 ? args.getMultiString(2)[0] : null;
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { supports, version, nonce == null ? new String[] {  } : new String[] { nonce } };
    }
}
