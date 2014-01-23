package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje waarmee een spel gestart wordt.
 * @author Pieter Bos
 */
public class StartGamePacket extends Packet {
    public StartGamePacket() {

    }

    /**
     * Het startGame pakket heeft geen argumenten.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {

    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] {  };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de argumenten van het pakket.
     */
    @Override
    protected Object[] getData() {
        return new Object[] {  };
    }
}
