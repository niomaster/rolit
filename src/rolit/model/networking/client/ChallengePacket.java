package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/** Het pakketje wat de challenge verstuurd naar spelers.
 * @author Pieter Bos
 */
public class ChallengePacket extends Packet {

    /**
     * De array met de spelers die gechallenged worden.
     */
    protected ChallengePacket() {

    }

    private String[] challenged;

    /**
     * De contructor van het pakktje.
     * @param challenged de spelers die gechallenged moeten worden.
     */
    public ChallengePacket(String[] challenged) {
        this.challenged = challenged;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @return de string gepresentatie van de spelers
     */
    public String[] getChallenged() {
        return challenged;
    }

    /**
     * Geeft de spelers die gechallenged zijn uit de argumenten van het pakket.
     * @param args de spelers.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.challenged = args.getMultiString(0);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { challenged };
    }
}
