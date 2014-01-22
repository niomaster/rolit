package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class ChallengePacket extends Packet {

    protected ChallengePacket() {

    }

    private String[] challenged;

    public ChallengePacket(String[] challenged) {
        this.challenged = challenged;
    }

    public String[] getChallenged() {
        return challenged;
    }

    @Override
    protected void readFromArgs(PacketArgs args) {
        this.challenged = args.getMultiString(0);
    }

    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { challenged };
    }
}
