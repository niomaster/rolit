package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het eerste pakketje wat gestuurd wordt.
 * @author Pieter Bos
 */

public class HandshakePacket extends Packet {
    private int supports;
    private String version;
    private String nonce;

    protected HandshakePacket() {

    }

    public HandshakePacket(int supports, String version, String nonce) {
        this.supports = supports;
        this.version = version;
        this.nonce = nonce;
    }

    public HandshakePacket(int supports, String version) {
        this(supports, version, null);
    }

    public int getSupports() {
        return supports;
    }

    public String getVersion() {
        return version;
    }

    public String getNonce() {
        return nonce;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.supports = args.getInt(0);
        this.version = args.getString(1);
        this.nonce = args.getMultiString(2).length == 1 ? args.getMultiString(2)[0] : null;
    }

    /**
     * Geeft het type de argumetnten.
     * @return het type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.MultiString };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array van wat de server support, de versie en de nonce.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { supports, version, nonce == null ? new String[] {  } : new String[] { nonce } };
    }
}
