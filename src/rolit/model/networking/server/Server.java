package rolit.model.networking.server;

import rolit.model.event.ServerListener;
import rolit.model.game.Game;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;
import rolit.util.Strings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Server extends ServerSocket implements Runnable {
    private static final int DEFAULT_BACKLOG = 5;
    public static final int GLOBAL_SUPPORTS = CommonProtocol.SUPPORTS_CHAT_CHALLENGE;
    public static final String GLOBAL_VERSION = "PieterMartijn_Alpha1";

    private Thread serverThread;
    private LinkedList<ServerListener> listeners = new LinkedList<ServerListener>();
    private LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
    private HashMap<String, User> users = new LinkedHashMap<String, User>();
    private HashMap<String, ServerGame> games = new LinkedHashMap<String, ServerGame>();

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
                handler.start();
                fireNewClient(handler);
            }
        } catch(IOException e) {
            fireServerError("IOException: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        new Server("0.0.0.0", 1234).serveForever();
    }

    public void notifyChallenged(String[] challengedUsers, String challenger) throws ProtocolException {
        for(String challengedUser : challengedUsers) {
            if(users.get(challengedUser) == null || users.get(challengedUser).getUsername() == null || !users.get(challengedUser).getClient().canBeChallenged()) {
                throw new ProtocolException("Client tried to challenge users that cannot be challenged or are not online or do not exist.", ServerProtocol.ERROR_GENERIC);
            }
        }

        for(String challengedUser : challengedUsers) {
            User user = users.get(challengedUser);
            user.getClient().notifyChallengedBy(challenger, Strings.remove(challengedUsers, challengedUser));
        }
    }

    public void setClientHandler(String clientName, ClientHandler clientHandler) {
        if(users.get(clientName) == null) {
            users.put(clientName, new User(clientName, clientHandler));
        } else {
            users.get(clientName).setClient(clientHandler);
        }
    }

    public void notifyChallengeResponse(boolean response, String[] userNames, String challenged) throws ProtocolException {
        for(String userName : userNames) {
            users.get(userName).getClient().notifyChallengeResponseBy(response, challenged);
        }
    }

    public ServerGame getGameByCreator(String creator) {
        return games.get(creator);
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public void createGame(String userName) throws ProtocolException {
        games.put(userName, new ServerGame(users.get(userName), this));
    }

    public void notifyOfGameChange(ServerGame game) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getUsername() != null) {
                user.getClient().notifyOfGameChange(game);;
            }
        }
    }

    public void writeInfo(String clientName) {
        User user = users.get(clientName);

        for(ServerGame game : games.values()) {
            user.getClient().notifyOfGameChange(game);
        }

        for(User other : users.values()) {
            if(other.getClient() != null && other.getClient().getClientName() != null && other.getClient().canChat()) {
                user.getClient().notifyOnlineOf(other.getUsername());
            }
        }
    }

    public void notifyOnline(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null) {
                user.getClient().notifyOnlineOf(clientName);
            }
        }
    }

    public void notifyOffline(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null) {
                user.getClient().notifyOfflineOf(clientName);
            }
        }
    }
}
