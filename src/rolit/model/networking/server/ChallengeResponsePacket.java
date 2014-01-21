package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengeResponsePacket extends Packet {
    private String user;
    private boolean accept;

    public ChallengeResponsePacket(String user, boolean accept) {
        this.user = user;
        this.accept = accept;
    }

    public String getUser() {
        return user;
    }

    public boolean isAccept() {
        return accept;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.accept = args.getBool(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { user, accept };
    }
}
