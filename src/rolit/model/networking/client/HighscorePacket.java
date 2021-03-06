package rolit.model.networking.client;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/** Het pakketje wat de highscores kan opvragen.
 * @author Pieter Bos
 */
public class HighscorePacket extends Packet {
    private String type;
    private String arg;

    protected HighscorePacket() {
        
    }

    public HighscorePacket(String type, String arg) {
        this.type = type;
        this.arg = arg;
    }

    public String getType() {
        return type;
    }

    public String getArg() {
        return arg;
    }

    /**
     * Zet de argumenten van het pakket in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.type = args.getString(0);
        this.arg = args.getString(1);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.String };
    }

    /**
     * Geeft de data van het pakket in een object array.
     * @return een object array met de het type van de highscore, en het argument daarbij.
     */
    @Override
    protected Object[] getData() {
        return new Object[] { type, arg };
    }
}
