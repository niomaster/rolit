package rolit.model.networking.common;

import rolit.model.networking.client.*;
import rolit.model.networking.server.ServerProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

public abstract class Packet {
    private static final LinkedHashMap<String, Class<? extends Packet>> CLIENT_PACKETS = new LinkedHashMap<String, Class<? extends Packet>>();
    private static final LinkedHashMap<String, Class<? extends Packet>> SERVER_PACKETS = new LinkedHashMap<String, Class<? extends Packet>>();

    static {
        CLIENT_PACKETS.put(ClientProtocol.HANDSHAKE, HandshakePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CREATE_GAME, CreateGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.JOIN_GAME, JoinGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.START_GAME, StartGamePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.MOVE, MovePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.MESSAGE, MessagePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CHALLENGE, ChallengePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.CHALLENGE_RESPONSE, ChallengeResponsePacket.class);
        CLIENT_PACKETS.put(ClientProtocol.HIGHSCORE, HighscorePacket.class);
    }

    protected abstract void readFromArgs(PacketArgs args);

    protected abstract PacketArgs.ArgumentType[] getArgumentTypes();

    protected abstract Object[] getData();

    private static Packet readFrom(BufferedReader input, LinkedHashMap<String, Class<? extends Packet>> packets) throws IOException, ProtocolException {
        String[] parts = input.readLine().split(CommonProtocol.COMMAND_DELIMITER);
        String command = parts[0];
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        Packet result = null;

        try {
            Class<? extends Packet> packetClass = packets.get(command);

            if(packetClass == null) {
                throw new ProtocolException("Can't parse unregistered command " + command, ServerProtocol.ERROR_GENERIC);
            } else {
                result = packetClass.newInstance();
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

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

    public void writeTo(PrintStream output) {
        output.print(new PacketArgs(getData()).toString());
        output.print(CommonProtocol.LINE_ENDING);
    }
}
