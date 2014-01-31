package rolit.model.networking.server;

import rolit.model.networking.client.*;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.Packet;
import rolit.model.networking.common.ProtocolException;
import rolit.util.Strings;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * De ClientHandler
 * @author Pieter Bos
 */
public class ClientHandler implements Runnable {
    private final Server server;
    private final Socket client;
    private BufferedReader input;
    private PrintStream output;
    private final Thread thread;

    private ClientHandlerState state;

    private int clientSupports;
    private String clientName;

    public ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        thread = new Thread(this);
        state = new InitialClientHandlerState(this);
    }

    /**
     * Start een nieuwe thread voor elke nieuwe client.
     */
    public void start() {
        thread.start();
    }

    /**
     * Verandert de status van de ClientHandlerState op basis de binnengekomen pakketjes.
     * @param packet Het pakketje wat ontvangen worden.
     * @throws ProtocolException wordt gegooid als er bij een van de pakketje die ontvangen wordt iets verkeerd gaat.
     */
    //@ requires packet instanceof
    //@ ensures \result new ClientHandlerState();
    private void handlePacket(Packet packet) throws ProtocolException {
        if(packet instanceof rolit.model.networking.client.ChallengePacket) {
            state = state.challenge((rolit.model.networking.client.ChallengePacket) packet);
        } else if(packet instanceof rolit.model.networking.client.ChallengeResponsePacket) {
            state = state.challengeResponse((rolit.model.networking.client.ChallengeResponsePacket) packet);
        } else if(packet instanceof CreateGamePacket) {
            state = state.createGame((CreateGamePacket) packet);
        } else if(packet instanceof rolit.model.networking.client.HandshakePacket) {
            state = state.handshake((rolit.model.networking.client.HandshakePacket) packet);
        } else if(packet instanceof rolit.model.networking.client.HighscorePacket) {
            highscore((rolit.model.networking.client.HighscorePacket) packet);
        } else if(packet instanceof JoinGamePacket) {
            state = state.joinGame((JoinGamePacket) packet);
        } else if(packet instanceof rolit.model.networking.client.MessagePacket) {
            message((rolit.model.networking.client.MessagePacket) packet);
        } else if(packet instanceof rolit.model.networking.client.MovePacket) {
            state = state.move((rolit.model.networking.client.MovePacket) packet);
        } else if(packet instanceof StartGamePacket) {
            state = state.startGame((StartGamePacket) packet);
        } else if(packet instanceof AuthPacket) {
            state = state.auth((AuthPacket) packet);
        } else {
            throw new ProtocolException("Client caused the server to be in an impossible condition", ServerProtocol.ERROR_GENERIC);
        }
    }


    private void message(rolit.model.networking.client.MessagePacket packet) throws ProtocolException {
        state = state.message(packet.getMessage());
    }

    private void highscore(rolit.model.networking.client.HighscorePacket packet) {
        if(packet.getType().equals("player")) {
            write(new HighscorePacket(new String[] { server.getPlayerHighscore(packet.getArg()) + "" }));
        } else if(packet.getType().equals("date")) {
            write(new HighscorePacket(new String[] { server.getDateHighscore(packet.getArg()) + "" }));
        } else {
            write(new HighscorePacket(new String[] { ServerProtocol.HIGHSCORE_UNAVAILABLE }));
        }
    }

    /**
     * Notified de spelers die gechallenged worden.
     * @param challenged de spelers die gechallenged worden.
     * @throws ProtocolException een exceptie als er iets fout gaat.
     */
    //@ requires challenged != null;
    public void notifyChallenged(String[] challenged) throws ProtocolException {
        server.notifyChallenged(challenged, getClientName());
    }

    @Override
    public void run() {
        try {
            try {
                this.input = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
                this.output = new PrintStream(client.getOutputStream(), true, "UTF-8");

                while(true) {
                    Packet packet = Packet.readClientPacketFrom(input);
                    server.lock();
                    handlePacket(packet);
                    server.unlock();
                }
            } catch (ProtocolException e) {
                server.fireClientError("ProtocolException: " + e.getMessage());
                new ErrorPacket(e.getCode()).writeTo(output);
                client.close();
            }
        } catch (IOException e) {
            server.lock();
            server.fireClientError("IOException: " + e.getMessage());
        }

        state = state.exit();

        if(getClientName() != null) {
            if(supportsChat()) {
                server.notifyOffline(getClientName());
            }

            if(supportsChallenge()) {
                server.notifyCannotBeChallenged(getClientName());
            }

            getUser().setClient(null);
        }

        server.unlock();
    }

    /* pure */
    public int getClientSupports() {
        return clientSupports;
    }

    //@ requires clientSupports == 0 || clientSupport == 1 || clientSupport == 2 || clientSupport == 3;
    //@ ensures getClientSupports == clientSupports;
    public void setClientSupports(int clientSupports) {
        this.clientSupports = clientSupports;
    }

    /* pure */
    public String getClientName() {
        return clientName;
    }

    //@ requires clientName != null;
    //@ ensures getClientName().equals(clientName);
    public void setClientName(String clientName) {
        this.clientName = clientName;
        server.setClientHandler(clientName, this);
    }

    public synchronized void write(Packet packet) {
        packet.writeTo(output);
    }

    //@ ensures \result true (if getClientSupports == 2 || getClientSupports == 3);
    public boolean supportsChallenge() {
        return (getClientSupports() & CommonProtocol.SUPPORTS_CHALLENGE) != 0;
    }

    //@ ensures \result true (if getClientSupports == 1 || getClientSupports == 3);
    public boolean supportsChat() {
        return (getClientSupports() & CommonProtocol.SUPPORTS_CHAT) != 0;
    }

    public boolean canBeChallenged() {
        return supportsChallenge() && state.canBeChallenged();
    }

    public void notifyChallengedBy(String challenger, String[] others) throws ProtocolException {
        state = state.notifyChallengedBy(challenger, others);
    }

    /**
     * Stuurt een notificatie naar de andere spelers die gechallenged waren, en de challenger.
     * @param response de reactie van de speler
     * @param challenger de challenger.
     * @param others de andere spelers die gechallenged zijn.
     * @throws ProtocolException wordt gegooid als er iets fout gaat.
     */
    public void notifyChallengeResponse(boolean response, String challenger, String[] others) throws ProtocolException {
        server.notifyChallengeResponse(response, Strings.push(challenger, others), getClientName());
    }

    /**
     * verandert de status van de speler die reageert op een challenge.
     * @param response de reactie van de speler.
     * @param challenged de speler die gechallenged is.
     * @throws ProtocolException wordt gegooid als er iets fout gaat.
     */
    public void notifyChallengeResponseBy(boolean response, String challenged) throws ProtocolException {
        state = state.notifyChallengeResponseBy(response, challenged);
    }

    /* pure */
    public ServerGame getGameByCreator(String creator) {
        return server.getGameByCreator(creator);
    }

    /* pure */
    public User getUser() {
        return server.getUser(getClientName());
    }

    //@ ensures getGameByCreator(getClientName) != null;
    public void createGame() throws ProtocolException {
        server.createGame(getClientName());
    }

    //@ requires game != null;
    public void notifyOfGameChange(ServerGame game) {
        state = state.notifyOfGameChange(game);
    }

    /* pure */
    public void writeInfo() {
        server.writeInfo(this.getClientName());
    }

    
    public void notifyOnline() {
        if(supportsChat()) {
            server.notifyOnline(getClientName());
        }
    }

    //@ requires
    public void notifyOnlineOf(String clientName) {
        if(supportsChat()) {
            write(new OnlinePacket(clientName, true));
        }
    }

    public void notifyOfflineOf(String clientName) {
        if(supportsChat()) {
            write(new OnlinePacket(clientName, false));
        }
    }

    public void notifyCannotBeChallengedOf(String clientName) {
        if(supportsChallenge()) {
            write(new CanBeChallengedPacket(clientName, false));
        }
    }

    public void notifyCanBeChallenged() {
        if(supportsChallenge()) {
            server.notifyCanBeChallenged(getClientName());
        }
    }

    public void notifyCanBeChallengedOf(String clientName) {
        if(supportsChallenge()) {
            write(new CanBeChallengedPacket(clientName, true));
        }
    }

    public void notifyCannotBeChallenged() {
        if(supportsChallenge()) {
            server.notifyCannotBeChallenged(getClientName());
        }
    }

    public void createChallengeGame(LinkedList<String> others) throws ProtocolException {
        server.createChallengeGame(getClientName(), others);
    }

    public void notifyOfGameStart(String[] names) throws ProtocolException {
        state = state.notifyOfGameStart(names);
    }

    public void notifyDoMove() throws ProtocolException {
        state = state.notifyDoMove();
    }

    public void notifyMove(String creator, int x, int y) throws ProtocolException {
        server.notifyMove(creator, getClientName(), x, y);
    }

    public void notifyOfMove(String mover, int x, int y) throws ProtocolException {
        state = state.notifyOfMove(mover, x, y);
    }

    public void notifyOfMessage(String clientName, String text) {
        write(new MessagePacket(clientName, text));
    }

    public void notifyMessage(String text) {
        server.broadcastMessage(getClientName(), text);
    }

    public void notifyOfBroadcast(String clientName, String text) {
        state = state.notifyOfBroadcast(clientName, text);
    }
}
