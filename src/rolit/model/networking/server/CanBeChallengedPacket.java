package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class CanBeChallengedPacket extends Packet {
    private String user;
    private boolean canBeChallenged;

    public CanBeChallengedPacket(String user, boolean canBeChallenged) {
        this.user = user;
        this.canBeChallenged = canBeChallenged;
    }

    public String getUser() {
        return user;
    }

    public boolean isCanBeChallenged() {
        return canBeChallenged;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.canBeChallenged = args.getBool(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { user, canBeChallenged };
    }
}
