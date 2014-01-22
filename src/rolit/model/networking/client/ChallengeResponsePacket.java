package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengeResponsePacket extends Packet {
    private boolean response;

    protected ChallengeResponsePacket() {

    }

    public ChallengeResponsePacket(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.response = args.getBool(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Boolean };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { response };
    }
}
