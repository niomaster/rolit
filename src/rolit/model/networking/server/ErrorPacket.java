package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje dat een error stuurt.
 * @author Pieter Bos
 */
public class ErrorPacket extends Packet {
    private int code;

    public ErrorPacket(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Zet de argumenten uit het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.code = args.getInt(0);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.Integer };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de error code.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { this.code };
    }
}
