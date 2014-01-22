package rolit.model.networking.common;

import rolit.model.networking.client.*;
import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.HandshakePacket;
import rolit.model.networking.client.HighscorePacket;
import rolit.model.networking.client.MessagePacket;
import rolit.model.networking.client.MovePacket;
import rolit.model.networking.server.*;
import rolit.util.BiMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * De basis voor alle pakketjes.
 * @author Pieter Bos
 */
public abstract class Packet {
    private static final BiMap<String, Class<? extends Packet>> CLIENT_PACKETS = new BiMap<String, Class<? extends Packet>>();
    private static final BiMap<String, Class<? extends Packet>> SERVER_PACKETS = new BiMap<String, Class<? extends Packet>>();

    static {
        CLIENT_PACKETS.put(ClientProtocol.HANDSHAKE, rolit.model.networking.client.HandshakePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.AUTH, AuthPacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CREATE_GAME, CreateGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.JOIN_GAME, JoinGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.START_GAME, StartGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.MOVE, MovePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.MESSAGE, rolit.model.networking.client.MessagePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CHALLENGE, rolit.model.networking.client.ChallengePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CHALLENGE_RESPONSE, rolit.model.networking.client.ChallengeResponsePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.HIGHSCORE, rolit.model.networking.client.HighscorePacket.class);

        SERVER_PACKETS.put(ServerProtocol.HANDSHAKE, rolit.model.networking.server.HandshakePacket.class);
        SERVER_PACKETS.put(ServerProtocol.AUTH_OK, AuthOkPacket.class);
        SERVER_PACKETS.put(ServerProtocol.ERROR, ErrorPacket.class);
        SERVER_PACKETS.put(ServerProtocol.GAME, GamePacket.class);
        SERVER_PACKETS.put(ServerProtocol.START, StartGamePacket.class);
        SERVER_PACKETS.put(ServerProtocol.MOVE, MovePacket.class);
        SERVER_PACKETS.put(ServerProtocol.MOVE_DONE, MoveDonePacket.class);
        SERVER_PACKETS.put(ServerProtocol.GAME_OVER, GameOverPacket.class);
        SERVER_PACKETS.put(ServerProtocol.MESSAGE, rolit.model.networking.server.MessagePacket.class);
        SERVER_PACKETS.put(ServerProtocol.CHALLENGE, rolit.model.networking.server.ChallengePacket.class);
        SERVER_PACKETS.put(ServerProtocol.CHALLENGE_RESPONSE, rolit.model.networking.server.ChallengeResponsePacket.class);
        SERVER_PACKETS.put(ServerProtocol.CAN_BE_CHALLENGED, CanBeChallengedPacket.class);
        SERVER_PACKETS.put(ServerProtocol.HIGHSCORE, rolit.model.networking.server.HighscorePacket.class);
        SERVER_PACKETS.put(ServerProtocol.ONLINE, OnlinePacket.class);
    }

    protected Packet() {

    }

    /**
     * Geeft de argumenten uit een bepaald pakket.
     * @param args de argumenten van het pakket.
     */
    protected abstract void readFromArgs(PacketArgs args);

    /**
     * Geeft het type van de argumenten van het pakket.
     * @return het type van de argumenten.
     */
    protected abstract PacketArgs.ArgumentType[] getArgumentTypes();

    /**
     * Geeft de data van het pakket.
     * @return een array van objeceten met de data.
     */
    protected abstract Object[] getData();

    private static Packet readFrom(BufferedReader input, BiMap<String, Class<? extends Packet>> packets) throws IOException, ProtocolException {
        String line = input.readLine();

        if(line == null) {
            throw new IOException("Could not read a line");
        }

        String[] parts = line.split(CommonProtocol.COMMAND_DELIMITER);
        String command = parts[0];
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        Packet result = null;

        try {
            Class<? extends Packet> packetClass = packets.get(command);

            if(packetClass == null) {
                throw new ProtocolException("Can't parse unregistered command " + command, ServerProtocol.ERROR_GENERIC);
            } else {
                Constructor<? extends Packet> constructor = packetClass.getDeclaredConstructor();

                if(constructor == null) {
                    throw new ProtocolException("Server cannot construct a packet for " + command, ServerProtocol.ERROR_GENERIC);
                } else {
                    constructor.setAccessible(true);
                    result = constructor.newInstance();
                }
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        }

        PacketArgs.ArgumentType[] args = result.getArgumentTypes();

        result.readFromArgs(PacketArgs.fromParts(parts, args));

        return result;
    }

    public static Packet readClientPacketFrom(BufferedReader input) throws IOException, ProtocolException {
        return readFrom(input, CLIENT_PACKETS);
    }

    public static Packet readServerPacketFrom(BufferedReader input) throws IOException, ProtocolException {
        return readFrom(input, SERVER_PACKETS);
    }

    public String getCommand() {
        return CLIENT_PACKETS.getBackward(this.getClass()) == null
                ? SERVER_PACKETS.getBackward(this.getClass())
                : CLIENT_PACKETS.getBackward(this.getClass());
    }

    public void writeTo(PrintStream output) {
        output.print(getCommand());
        output.print(CommonProtocol.COMMAND_DELIMITER);
        output.print(new PacketArgs(getData()).toString());
        output.print(CommonProtocol.LINE_ENDING);
    }
}
