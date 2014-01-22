package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

public class MessagePacket extends Packet {
    private String message;

    public MessagePacket(String message) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Zet de argumenten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.message = args.getString(0);
    }

    /**
     * Geeft het type van het argument.
     * @return het type argument: string.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return de dat
     */
    @Override
    protected Object[] getData() {
        return new Object[] { message };
    }
}
