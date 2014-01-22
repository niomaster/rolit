package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class AuthPacket extends Packet {
    private String cypherText;

    protected AuthPacket() {

    }

    public AuthPacket(String cypherText) {
        this.cypherText = cypherText;
    }

    public String getCypherText() {
        return cypherText;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.cypherText = args.getString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { cypherText };
    }
}
