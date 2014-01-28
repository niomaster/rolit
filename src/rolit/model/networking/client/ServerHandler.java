package rolit.model.networking.client;

import rolit.model.networking.common.*;
import rolit.model.networking.common.ProtocolException;

import java.io.*;
import java.net.*;

public class ServerHandler {
    private static final String GLOBAL_VERSION = "PieterMartijn_Alpha1";
    private static final int GLOBAL_SUPPORTS = CommonProtocol.SUPPORTS_CHAT_CHALLENGE;
    private Socket socket;

    private PrintStream output;
    private BufferedReader input;

    public ServerHandler(String hostName, int port) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(hostName), port));

        input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        output = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true, "UTF-8");
    }

    private Packet readPacket() throws IOException, rolit.model.networking.common.ProtocolException {
        return Packet.readServerPacketFrom(input);
    }

    public void handshake(String userName) {
        new HandshakePacket(userName, GLOBAL_SUPPORTS, GLOBAL_VERSION).writeTo(output);
    }

    public <T extends Packet> T expectPacket(Class<T> c) throws IOException, ProtocolException {
        Packet packet = readPacket();

        if(c.isAssignableFrom(packet.getClass())) {
            return (T) packet;
        } else {
            throw new ProtocolException("Server sent a packet that the client did not expect.");
        }
    }
}
