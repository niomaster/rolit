package rolit.model.networking.server;

import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketArgs;

/**
 * Het pakketje met de informatie van een game.
 * @author Pieter Bos
 */
public class GamePacket extends Packet {
    private String game;
    private int status;
    private int players;

    public GamePacket(String game, int status, int players) {
        this.game = game;
        this.status = status;
        this.players = players;
    }

    public String getGame() {
        return game;
    }

    public int getStatus() {
        return status;
    }

    public int getPlayers() {
        return players;
    }

    /**
     * Zet de argumten van het pakketje in variabele.
     * @param args de argumenten van het pakket.
     */
    @Override
    protected void readFromArgs(PacketArgs args) {
        this.game = args.getString(0);
        this.status = args.getInt(1);
        this.players = args.getInt(2);
    }

    /**
     * Geeft het type van het argument van het pakketje.
     * @return type argument.
     */
    @Override
    protected PacketArgs.ArgumentType[] getArgumentTypes() {
        return new PacketArgs.ArgumentType[] { PacketArgs.ArgumentType.String, PacketArgs.ArgumentType.Integer, PacketArgs.ArgumentType.Integer };
    }

    @Override
    protected Object[] getData() {
        return new Object[] { game, status, players };
    }
}
