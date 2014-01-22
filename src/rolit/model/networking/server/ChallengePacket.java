package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengePacket extends Packet {
    private String challenger;
    private String[] others;

    protected ChallengePacket() {

    }

    public ChallengePacket(String challenger, String[] others) {
        this.challenger = challenger;
        this.others = others;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.challenger = args.getString(0);
        this.others = args.getMultiString(1);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { challenger, others };
    }
}
