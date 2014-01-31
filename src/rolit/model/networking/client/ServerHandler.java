package rolit.model.networking.client;

import rolit.model.networking.common.*;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.CanBeChallengedPacket;
import rolit.model.networking.server.GamePacket;
import rolit.model.networking.server.OnlinePacket;
import rolit.util.Arrays;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;

public class ServerHandler {
    private static final String GLOBAL_VERSION = "PieterMartijn_Alpha1";
    private static final int GLOBAL_SUPPORTS = CommonProtocol.SUPPORTS_CHAT_CHALLENGE;
    private Socket socket;

    private static final Class[] IGNORE_TYPES = new Class[] {
            GamePacket.class,
            rolit.model.networking.server.MessagePacket.class,
            rolit.model.networking.server.ChallengePacket.class,
            OnlinePacket.class,
            CanBeChallengedPacket.class
    };

    private PrintStream output;
    private PacketSource source;
    private PacketInputStream input;
    private String hostName;
    private int port;

    public ServerHandler(String hostName, int port) throws IOException {
        this.hostName = hostName;
        this.port = port;
        socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(hostName), port));

        source = new PacketSource(socket.getInputStream());
        input = source.createInputStream();
        output = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true, "UTF-8");
    }

    public PacketSource getSource() {
        return source;
    }

    private Packet readPacket() throws IOException, rolit.model.networking.common.ProtocolException {
        return input.readPacket();
    }

    public void handshake(String userName) {
        new HandshakePacket(userName, GLOBAL_SUPPORTS, GLOBAL_VERSION).writeTo(output);
    }

    public Packet expectPacket(Class<? extends Packet>[] expect, boolean ignoreGame) throws IOException, ProtocolException {
        Packet packet = readPacket();

        while(Arrays.contains(IGNORE_TYPES, packet.getClass()) && (ignoreGame || packet.getClass() != GamePacket.class)) {
            packet = readPacket();
        }

        for(Class<? extends Packet> c : expect) {
            if(c.isAssignableFrom(packet.getClass())) {
                return packet;
            }
        }

        System.out.println("Onverwacht pakket: " + packet.getClass().getName());

        throw new ProtocolException("De server stuurde een pakket dat de client niet verwachtte.");
    }

    public <T extends Packet> T expectPacket(Class<T> c) throws IOException, ProtocolException {
        return (T) expectPacket(new Class[] { c }, true);
    }

    public PacketInputStream createPacketInputStream() {
        return source.createInputStream();
    }

    public void start() {
        source.start();
    }

    public void auth(String sign) {
        new AuthPacket(sign).writeTo(output);
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public void createGame() {
        new CreateGamePacket().writeTo(output);
    }

    public void joinGame(String creator) {
        new JoinGamePacket(creator).writeTo(output);
    }

    public void startGame() {
        new StartGamePacket().writeTo(output);
    }

    public void move(int x, int y) {
        new MovePacket(x, y).writeTo(output);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void highscore(String type, String arg) {
        new HighscorePacket(type, arg).writeTo(output);
    }

    public void challenge(String[] challenged) {
        new ChallengePacket(challenged).writeTo(output);
    }

    public void message(String text) {
        new MessagePacket(text).writeTo(output);
    }

    public void challengeResponse(boolean accept) {
        new ChallengeResponsePacket(accept).writeTo(output);
    }
}
