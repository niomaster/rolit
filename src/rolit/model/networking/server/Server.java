package rolit.model.networking.server;

import rolit.model.event.ServerListener;
import rolit.model.game.Game;
import rolit.model.networking.common.CommonProtocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server extends ServerSocket implements Runnable {
    private static final int DEFAULT_BACKLOG = 5;
    public static final int GLOBAL_SUPPORTS = CommonProtocol.SUPPORTS_CHAT_CHALLENGE;
    public static final String GLOBAL_VERSION = "PieterMartijn_Alpha1";

    private Thread serverThread;
    private LinkedList<ServerListener> listeners = new LinkedList<ServerListener>();
    private LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
    private LinkedList<User> users = new LinkedList<User>();
    private LinkedList<ServerGame> games = new LinkedList<ServerGame>();

    public Server(String bindAddress, int port) throws IOException {
        super(port, DEFAULT_BACKLOG, InetAddress.getByName(bindAddress));
        serverThread = new Thread(this);
    }

    public Thread getServerThread() {
        return serverThread;
    }

    public void addListener(ServerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ServerListener listener) {
        listeners.remove(listener);
    }

    public void serveForever() {
        serverThread.start();
    }

    public void fireServerError(String reason) {
        for(ServerListener listener : listeners) {
            listener.serverError(reason);
        }
    }

    private void fireNewClient(ClientHandler handler) {
        for(ServerListener listener : listeners) {
            listener.newClient(handler);
        }
    }

    public void fireClientError(String reason) {
        for(ServerListener listener : listeners) {
            listener.clientError(reason);
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket client = accept();
                ClientHandler handler = new ClientHandler(this, client);
                fireNewClient(handler);
            }
        } catch(IOException e) {
            fireServerError("IOException: " + e.getMessage());
        }
    }
}
