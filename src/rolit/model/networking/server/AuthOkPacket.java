package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje dat gestuurd wordt als de authenticatie gelukt is.
 * @author Pieter Bos
 */
public class AuthOkPacket extends Packet {
    public AuthOkPacket() {

    }

    /**
     *
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {

    }

    /**
     * Geeft het type van het argument terug.
     * @return het type van het argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] {  };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array van de argumenten van het pakketje.
     */
    @Override
    protected Object[] getData() {
        return new Object[] {  };
    }
}
