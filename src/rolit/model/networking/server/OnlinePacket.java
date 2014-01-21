package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class OnlinePacket extends Packet {
    private String user;
    private boolean online;

    public OnlinePacket(String user, boolean online) {
        this.user = user;
        this.online = online;
    }

    public String getUser() {
        return user;
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.user = args.getString(0);
        this.online = args.getBool(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Boolean }
    }

    @Override
    protected Object[] getData() {
        return new Object[] { user, online };
    }
}
