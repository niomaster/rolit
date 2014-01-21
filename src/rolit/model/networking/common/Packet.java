package rolit.model.networking.common;

import rolit.model.networking.client.HandshakePacket;
import rolit.model.networking.server.ServerProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

public abstract class Packet {
    private static final LinkedHashMap<String, Class<? extends Packet>> PACKETS = new LinkedHashMap<String, Class<? extends Packet>>();

    static {
        PACKETS.put(ServerProtocol.HANDSHAKE, HandshakePacket.class);

    }

    public enum ArgumentType {
        Integer,
        Boolean,
        String,
        MultiString
    }

    protected abstract void readFromArgs(PacketArgs args);

    protected abstract ArgumentType[] getArgumentTypes();

    protected abstract Object[] getData();

    public static Packet readFrom(BufferedReader input) throws IOException, ProtocolException {
        String[] parts = input.readLine().split(CommonProtocol.COMMAND_DELIMITER);
        String command = parts[0];
        parts = Arrays.copyOfRange(parts, 1, parts.length);

        Packet result = null;

        try {
            Class<? extends Packet> packetClass = PACKETS.get(command);

            if(packetClass == null) {
                throw new ProtocolException("Can't parse unregistered command " + command, ServerProtocol.ERROR_GENERIC);
            } else {
                result = packetClass.newInstance();
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        ArgumentType[] args = result.getArgumentTypes();

        result.readFromArgs(PacketArgs.fromParts(parts, args));

        return result;
    }

    public void writeTo(PrintStream output) {
        output.print(new PacketArgs(getData()).toString());
        output.print(CommonProtocol.LINE_ENDING);
    }
}
