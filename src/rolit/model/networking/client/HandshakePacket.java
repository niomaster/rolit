package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakket dat als eerste wordt gestuurd, om de verbinding op te zetten.
 * @author Pieter Bos
 */
public class HandshakePacket extends Packet {
    private String name;
    private int supports;
    private String version;

    protected HandshakePacket() {

    }

    public HandshakePacket(String name, int supports, String version) {

        this.name = name;
        this.supports = supports;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public int getSupports() {
        return supports;
    }

    public String getVersion() {
        return version;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.name = args.getString(0);
        this.supports = args.getInt(1);
        this.version = args.getString(2);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.String };
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { name, supports, version };
    }


}
