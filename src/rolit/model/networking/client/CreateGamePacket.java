package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/** Het pakket wat ervoor zorgt dat er een game gemaakt wordt.
 * @author Pieter Bos
 */
public class CreateGamePacket extends Packet {

    public CreateGamePacket() {

    }

    /**
     * Zet de argumenten van het pakket in variabele, CreateGame heeft geen argumenten.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {

    }

    /**
     * Geeft het type van de argumenten van het pakket, CreateGame heeft geen argumenten
     * @return het type argumenten van het pakket.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] {  };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de data van het pakket
     */
    @Override
    protected Object[] getData() {
        return new Object[] {  };
    }
}
