package rolit.model.networking.server;

import rolit.model.networking.client.ClientProtocol;
import rolit.model.networking.common.Command;
import rolit.model.networking.common.ProtocolException;
import rolit.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends ServerProtocol implements Runnable {
    private final Server server;
    private final Socket client;
    private BufferedReader input;
    private PrintStream output;
    private final Thread thread;

    private int clientSupports;
    private String clientVersion;
    private User user;
    private ServerGame currentGame;
    private String[] currentChallenge;

    public ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        thread = new Thread(this);
    }

    public int getClientSupports() {
        return clientSupports;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public boolean isInGame() {
        return currentGame != null;
    }

    public User getUser() {
        return user;
    }

    public void start() {
        thread.start();
    }

    @Override
    public synchronized void handshake(int supports, String version) throws IOException {
        new Command(ServerProtocol.HANDSHAKE, supports, version).writeTo(output);
    }

    @Override
    public void handshake(int supports, String version, String nonce) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void authOk() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public synchronized void error(int errorCode) throws IOException {
        new Command(ServerProtocol.ERROR, errorCode).writeTo(output);
    }

    @Override
    public synchronized void game(String creator, boolean hasStarted, int noPlayers) throws IOException {
        new Command(ServerProtocol.GAME, creator, hasStarted, noPlayers).writeTo(output);
    }

    @Override
    public synchronized void start(String playerOne, String playerTwo) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo).writeTo(output);
    }

    @Override
    public synchronized void start(String playerOne, String playerTwo, String playerThree) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo, playerThree).writeTo(output);
    }

    @Override
    public synchronized void start(String playerOne, String playerTwo, String playerThree, String playerFour) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo, playerThree, playerFour).writeTo(output);
    }

    @Override
    public synchronized void move() throws IOException {
        new Command(ServerProtocol.MOVE).writeTo(output);
    }

    @Override
    public synchronized void moveDone(String name, int x, int y) throws IOException {
        new Command(ServerProtocol.MOVE_DONE, x, y).writeTo(output);
    }

    @Override
    public synchronized void gameOver(int score, String[] winners) throws IOException {
        new Command(ServerProtocol.GAME_OVER, score, winners).writeTo(output);
    }

    @Override
    public synchronized void message(String name, String body) throws IOException {
        new Command(ServerProtocol.MESSAGE, name, body).writeTo(output);
    }

    @Override
    public synchronized void challenge(String challenger, String other1) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1).writeTo(output);
    }

    @Override
    public synchronized void challenge(String challenger, String other1, String other2) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1, other2).writeTo(output);
    }

    @Override
    public synchronized void challenge(String challenger, String other1, String other2, String other3) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1, other2, other3).writeTo(output);
    }

    @Override
    public synchronized void challengeResponse(String name, boolean accept) throws IOException {
        new Command(ServerProtocol.CHALLENGE_RESPONSE, name, accept).writeTo(output);
    }

    @Override
    public synchronized void canBeChallenged(String name, boolean flag) throws IOException {
        new Command(ServerProtocol.CAN_BE_CHALLENGED, name, flag).writeTo(output);
    }

    @Override
    public synchronized void highscore(String[] args) throws IOException {
        new Command(ServerProtocol.HIGHSCORE, args).writeTo(output);
    }

    @Override
    public synchronized void online(String name, boolean isOnline) throws IOException {
       new Command(ServerProtocol.ONLINE, name, isOnline).writeTo(output);
    }

    private void handlePacket(Command command) throws ProtocolException {
        if(command.getCommand().equals(ClientProtocol.HANDSHAKE)) {
            throw new ProtocolException("Client must not send a handshake after the initial handshake.", ServerProtocol.ERROR_GENERIC);
        } else if(command.getCommand().equals(ClientProtocol.CREATE_GAME)) {
            if(isInGame()) {
                throw new ProtocolException("Client must not start a game while in a game.", ServerProtocol.ERROR_USER_ALREADY_HAS_GAME);
            }

            ServerGame game = new ServerGame(user);

            server.addGame(game);
            currentGame = game;
        } else if(command.getCommand().equals(ClientProtocol.JOIN_GAME)) {
            if(isInGame()) {
                throw new ProtocolException("Client must not start a game while in a game.", ServerProtocol.ERROR_USER_ALREADY_HAS_GAME);
            }

            ServerGame game = server.getGame((String) command.getArgument(0));

            if(game == null) {
                throw new ProtocolException("Client tried to join a game that doesn't exist.", ServerProtocol.ERROR_NO_SUCH_GAME);
            }

            game.addPlayer(user);

            currentGame = game;
        } else if(command.getCommand().equals(ClientProtocol.START_GAME)) {
            if(currentGame == null) {
                throw new ProtocolException("Client must not try to start while not in a game.", ServerProtocol.ERROR_USER_HAS_NO_GAME);
            }

            if(currentGame.getCreator() != user) {
                throw new ProtocolException("Client must be the creator of the game to start.", ServerProtocol.ERROR_GENERIC);
            }

            if(currentGame.isStarted()) {
                throw new ProtocolException("Client must be in a game that is not started to start", ServerProtocol.ERROR_GENERIC);
            }

            currentGame.start();
        } else if(command.getCommand().equals(ClientProtocol.MOVE)) {
            int x = (Integer) command.getArgument(0), y = (Integer) command.getArgument(1);

            if(currentGame == null) {
                throw new ProtocolException("Client must be in a game to do a move.", ServerProtocol.ERROR_USER_HAS_NO_GAME);
            }

            if(!currentGame.isNext(user)) {
                throw new ProtocolException("Client must be next to do a move.", ServerProtocol.ERROR_GENERIC);
            }

            if(!currentGame.canDoMove(x, y)) {
                throw new ProtocolException("Client must do a move within the board.", ServerProtocol.ERROR_INVALID_MOVE);
            }

            currentGame.doMove(x, y);
        } else if(command.getCommand().equals(ClientProtocol.MESSAGE)) {
            String message = Strings.join(" ", (String[]) command.getArgument(0));

            if(currentGame == null) {
                server.broadcastMessage(user, message);
            } else {
                server.gameMessage(user, message, currentGame);
            }
        } else if(command.getCommand().equals(ClientProtocol.CHALLENGE)) {
            server.challenge(user, (String[]) command.getArgument(0));
            currentChallenge = (String[]) command.getArgument(0);
        } else if(command.getCommand().equals(ClientProtocol.CHALLENGE_RESPONSE)) {
            if(currentChallenge == null) {
                error(ServerProtocol.E)
            }
        }
    }

    @Override
    public void run() {
        try {
            try {
                this.input = getBufferedReader(client.getInputStream());
                this.output = getPrintStream(client.getOutputStream());

                Command handshake = Command.readFromClient(input);

                if(!handshake.getCommand().equals(ServerProtocol.HANDSHAKE)) {
                    throw new ProtocolException("Client did not send a handshake as first packet", ServerProtocol.ERROR_HANDSHAKE_MISSING);
                }

                user = server.authenticateUser((String) handshake.getArgument(0));
                clientSupports = (Integer) handshake.getArgument(1);
                clientVersion = (String) handshake.getArgument(2);

                if(user == null) {
                    throw new ProtocolException("Invalid authentication for user " + ((String) handshake.getArgument(0)), ServerProtocol.ERROR_INVALID_LOGIN);
                }

                handshake(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION);

                while(true) {
                    handlePacket(Command.readFromClient(input));
                }
            } catch (ProtocolException e) {
                server.fireClientError("ProtocolException: " + e.getMessage());
                error(e.getCode());
                client.close();
            }
        } catch (IOException e) {
            server.fireClientError("IOException: " + e.getMessage());
        }
    }
}
