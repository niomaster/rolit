package rolit.model.networking.server;

import rolit.model.event.ServerListener;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;
import rolit.util.Strings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * De server.
 * @author Pieter Bos
 */
public class Server extends ServerSocket implements Runnable {

    private static final int DEFAULT_BACKLOG = 5;
    public static final int GLOBAL_SUPPORTS = CommonProtocol.SUPPORTS_CHAT_CHALLENGE;
    // TODO change global version
    public static final String GLOBAL_VERSION = "PieterMartijn_Alpha1";

    private Thread serverThread;
    private LinkedList<ServerListener> listeners = new LinkedList<ServerListener>();
    private HashMap<String, User> users = new LinkedHashMap<String, User>();
    private HashMap<String, ServerGame> games = new LinkedHashMap<String, ServerGame>();
    private LeaderBoard<Integer> leaderBoard = new LeaderBoard<Integer>();

    private Lock lock = new ReentrantLock();

    //@ requires port >= 0 && port < 65535;
    public Server(String bindAddress, int port) throws IOException {
        super(port, DEFAULT_BACKLOG, InetAddress.getByName(bindAddress));
        serverThread = new Thread(this);
    }


    /* pure */
    public Thread getServerThread() {
        return serverThread;
    }

    /**
     * Voegt een nieuwe listener toe aan de lijst van listeners
     * @param listener de nieuwe listener.
     */
    //@ requires listener != null;
    public void addListener(ServerListener listener) {
        listeners.add(listener);
    }

    /**
     * Verwijdert een listener uit de lijst van listeners.
     * @param listener de listener die verwijders moet worden.
     */
    //@ requires listener != null;
    //@ ensures !listeners.contains(listener);
    public void removeListener(ServerListener listener) {
        listeners.remove(listener);
    }

    /**
     * Bekijkt of een listener in de lijst van listener zit.
     * @param listener de listener die gecontroleerd wordt.
     * @return Een boolean of de listener in de lijst van listeners zit.
     */
    //@ requires listener != null;
    //@ ensures \result false if(listeners.contains(listener));
    public boolean isInListeners(ServerListener listener){
        return listeners.contains(listener);
    }

    /**
     * Start de server thread.
     *
     */
    public void serveForever() {
        serverThread.start();
    }

    /**
     * verstuurd een nieuwe error.
     * @param reason de rede voor de error.
     */
    //@
    public void fireServerError(String reason) {
        for(ServerListener listener : listeners) {
            listener.serverError(reason);
        }
    }

    /**
     * Voegt voor alle listeners de nieuwe ClientHandler toe.
     * @param handler de nieuwe ClientHandler.
     */
    private void fireNewClient(ClientHandler handler) {
        for(ServerListener listener : listeners) {
            listener.newClient(handler);
        }
    }

    /**
     * Verstuurd een error voor alle listeners.
     * @param reason de rede voor de error.
     */
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

    /**
     * Notified alle spelers die zijn uitgedaagd.
     * @param challengedUsers de spelers die zijn uitgedaag.
     * @param challenger de speler die uitdaagd.
     * @throws ProtocolException
     */
    //@ requires challengedUsers.size > 0;
    public void notifyChallenged(String[] challengedUsers, String challenger) throws ProtocolException {
        for(String challengedUser : challengedUsers) {
            if(users.get(challengedUser) == null || users.get(challengedUser).getUsername() == null || !users.get(challengedUser).getClient().supportsChallenge()) {
                throw new ProtocolException("Client tried to challenge users that cannot be challenged or are not online or do not exist.", ServerProtocol.ERROR_GENERIC);
            }
        }

        for(String challengedUser : challengedUsers) {
            User user = users.get(challengedUser);
            user.getClient().notifyChallengedBy(challenger, Strings.remove(challengedUsers, challengedUser));
        }
    }

    /**
     * Geeft een client een ClientHandler.
     * @param clientName de naam van de speler
     * @param clientHandler de ClientHandler.
     */
    //@ requires clientName != null;
    //@ requires clientHandler != null;
    //@ ensures user.getClientHandler == clientHandler;
    public void setClientHandler(String clientName, ClientHandler clientHandler) {
        if(users.get(clientName) == null) {
            users.put(clientName, new User(clientName, clientHandler));
        } else {
            users.get(clientName).setClient(clientHandler);
        }
    }

    /**
     * Verstuurd een notificatie naar de challenger, en de andere speler die challenged zijn over de reactie van een
     * speler die gechallenged is.
     * @param response de reactie van de speler.
     * @param userNames de andere spelers die zijn uitgedaagd.
     * @param challenged de naam van de speler die een reactie geeft.
     * @throws ProtocolException wordt gegooid als er iets fout gaat.
     */
    //@ ensures
    public void notifyChallengeResponse(boolean response, String[] userNames, String challenged) throws ProtocolException {
        for(String userName : userNames) {
            users.get(userName).getClient().notifyChallengeResponseBy(response, challenged);
        }
    }

    //@ requires creator != null;
    //@ ensures \result
   public ServerGame getGameByCreator(String creator) {
        return games.get(creator);
    }

    /* pure */
    public User getUser(String userName) {
        return users.get(userName);
    }

    /**
     * Maakt een nieuwe game aan.
     * @param userName de naam van de speler die de game aanmaakt.
     */
    //@ requires userName != null;
    //@ ensures
    public void createGame(String userName) throws ProtocolException {
        games.put(userName, new ServerGame(users.get(userName), this));
    }

    /**
     * Stuurt een notificatie aan alle andere clients over de verandering van het spel.
     * @param game de spel waarover de verandering gaat.
     */
    //@ requires game != null;
    //@ ensures
    public void notifyOfGameChange(ServerGame game) {
        if(game.isStopped() && game.isStarted()) {
            games.remove(game.getCreator().getUsername());
            Date date = new Date();

            for(String winner : game.getWinners()) {
                leaderBoard.add(new Score(game.getScore(), winner, date));
            }
        }

        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null) {
                user.getClient().notifyOfGameChange(game);
            }
        }
    }

    public void writeInfo(String clientName) {
        User user = users.get(clientName);

        for(ServerGame game : games.values()) {
            user.getClient().notifyOfGameChange(game);
        }

        for(User other : users.values()) {
            if(other.getClient() != null && other.getClient().getClientName() != null && other.getClient().supportsChat()) {
                user.getClient().notifyOnlineOf(other.getUsername());
            }
        }

        for(User other : users.values()) {
            if(other.getClient() != null && other.getClient().getClientName() != null && other.getClient().canBeChallenged()) {
                user.getClient().notifyCanBeChallengedOf(other.getUsername());
            }
        }
    }

    public void notifyOnline(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null && !user.getClient().getClientName().equals(clientName)) {
                user.getClient().notifyOnlineOf(clientName);
            }
        }
    }

    public void notifyOffline(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null && !user.getClient().getClientName().equals(clientName)) {
                user.getClient().notifyOfflineOf(clientName);
            }
        }
    }

    public void notifyCannotBeChallenged(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null && !user.getClient().getClientName().equals(clientName)) {
                user.getClient().notifyCannotBeChallengedOf(clientName);
            }
        }
    }

    public void notifyCanBeChallenged(String clientName) {
        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null  && !user.getClient().getClientName().equals(clientName)) {
                user.getClient().notifyCanBeChallengedOf(clientName);
            }
        }
    }

    public void createChallengeGame(String challenger, LinkedList<String> others) throws ProtocolException {
        ServerGame game = new ServerGame(challenger, others, this);
        game.start();
    }

    public void notifyOfGameStart(ServerGame serverGame) throws ProtocolException {
        String[] names = new String[serverGame.getPlayerCount()];

        for(int i = 0; i < names.length; i++) {
            names[i] = serverGame.getPlayers().get(i).getUsername();
        }

        for(User player : serverGame.getPlayers()) {
            player.getClient().notifyOfGameStart(names);
        }
    }

    public void notifyMove(String creator, String mover, int x, int y) throws ProtocolException {
        ServerGame game = getGameByCreator(creator);

        for(User player : game.getPlayers()) {
            player.getClient().notifyOfMove(mover, x, y);
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public int getPlayerHighscore(String s) {
        if(leaderBoard.getPlayer(s) == null) {
            return -1;
        } else {
            return leaderBoard.getPlayer(s).getScore().intValue();
        }
    }


    public int getDateHighscore(String s) {
        try {
            Score sc = leaderBoard.getDay(new SimpleDateFormat("yyyy-MM-dd").parse(s));
            if(sc != null) {
                return sc.getScore().intValue();
            } else {
                return -1;
            }
        } catch (ParseException e) {
            return -1;
        }
    }

    public void broadcastMessage(String clientName, String text) {
        fireClientMessage(clientName, text);

        for(User user : users.values()) {
            if(user.getClient() != null && user.getClient().getClientName() != null) {
                user.getClient().notifyOfBroadcast(clientName, text);
            }
        }
    }

    private void fireClientMessage(String clientName, String text) {
        for(ServerListener listener : listeners) {
            listener.clientMessage(clientName, text);
        }
    }

    public int getOverallHighscore() {
        if(leaderBoard.getMax() == null) {
            return -1;
        } else {
            return leaderBoard.getMax().getScore().intValue();
        }
    }
}
