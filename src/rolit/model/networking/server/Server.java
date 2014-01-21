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

    public synchronized User authenticateUser(String username) {
        boolean exists = false;
        User theUser = null;

        for(User user : users) {
            if(user.getUsername().equals(username)) {
                exists = true;
                theUser = user;
                break;
            }
        }

        if(!exists) {
            User user = new User(username);
            users.add(user);
            return user;
        } else {
            for(ClientHandler client : clients) {
                if(client.getUser() == theUser) {
                    return null;
                }
            }

            return theUser;
        }
    }

    public synchronized void addGame(ServerGame game) {
        games.add(game);
    }

    public synchronized ServerGame getGame(String creator) {
        for(ServerGame game : games) {
            if(game.getCreator().getUsername().equals(creator)) {
                return game;
            }
        }

        return null;
    }

    public synchronized void broadcastMessage(User user, String message) {
        for(ClientHandler client : clients) {
            try {
                client.message(user.getUsername(), message);
            } catch(IOException e) {
                // Wait for client to be removed by ClientHandler
            }
        }
    }

    public synchronized void gameMessage(User user, String join, ServerGame game) {
//        for(User gameUser : game.getPlayers()) {
//            for(ClientHandler client : clients) {
//                if(client.getUser() == gameUser) {
//                    try {
//                        client.message(user.getUsername(), join);
//                    } catch (IOException e) {
//
//                    }
//                }
//            }
//        }
    }

    public void challenge(User user, String[] others) {
        for(String userName : others) {
            for(ClientHandler client : clients) {
                if(client.getUser().getUsername().equals(userName)) {
                    try {
                        switch(others.length) {
                            case 1:
                                client.challenge(user.getUsername(), others[0]);
                                break;
                            case 2:
                                client.challenge(user.getUsername(), others[0], others[1]);
                                break;
                            case 3:
                                client.challenge(user.getUsername(), others[0], others[1], others[2]);
                        }
                    } catch (IOException e) {

                    }
                }
            }
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
