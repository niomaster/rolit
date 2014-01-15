package rolit.model.networking.server;

import rolit.model.networking.client.ClientProtocol;
import rolit.model.networking.common.Command;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;

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

    public ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void handshake(int supports, String version) throws IOException {
        new Command(ServerProtocol.HANDSHAKE, supports, version).writeTo(output);
    }

    @Override
    public void error(int errorCode) throws IOException {
        new Command(ServerProtocol.ERROR, errorCode).writeTo(output);
    }

    @Override
    public void game(String creator, boolean hasStarted, int noPlayers) throws IOException {
        new Command(ServerProtocol.GAME, creator, hasStarted, noPlayers).writeTo(output);
    }

    @Override
    public void start(String playerOne, String playerTwo) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo).writeTo(output);
    }

    @Override
    public void start(String playerOne, String playerTwo, String playerThree) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo, playerThree).writeTo(output);
    }

    @Override
    public void start(String playerOne, String playerTwo, String playerThree, String playerFour) throws IOException {
        new Command(ServerProtocol.START, playerOne, playerTwo, playerThree, playerFour).writeTo(output);
    }

    @Override
    public void move() throws IOException {
        new Command(ServerProtocol.MOVE).writeTo(output);
    }

    @Override
    public void moveDone(String name, int x, int y) throws IOException {
        new Command(ServerProtocol.MOVE_DONE, x, y).writeTo(output);
    }

    @Override
    public void gameOver(int score, String[] winners) throws IOException {
        new Command(ServerProtocol.GAME_OVER, score, winners).writeTo(output);
    }

    @Override
    public void message(String name, String body) throws IOException {
        new Command(ServerProtocol.MESSAGE, name, body).writeTo(output);
    }

    @Override
    public void challenge(String challenger, String other1) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1).writeTo(output);
    }

    @Override
    public void challenge(String challenger, String other1, String other2) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1, other2).writeTo(output);
    }

    @Override
    public void challenge(String challenger, String other1, String other2, String other3) throws IOException {
        new Command(ServerProtocol.CHALLENGE, challenger, other1, other2, other3).writeTo(output);
    }

    @Override
    public void challengeResponse(String name, boolean accept) throws IOException {
        new Command(ServerProtocol.CHALLENGE_RESPONSE, name, accept).writeTo(output);
    }

    @Override
    public void canBeChallenged(String name, boolean flag) throws IOException {
        new Command(ServerProtocol.CAN_BE_CHALLENGED, name, flag).writeTo(output);
    }

    @Override
    public void highscore(String[] args) throws IOException {
        new Command(ServerProtocol.HIGHSCORE, args).writeTo(output);
    }

    @Override
    public void online(String name, boolean isOnline) throws IOException {
       new Command(ServerProtocol.ONLINE, name, isOnline).writeTo(output);
    }

    private void handlePacket(Command command) throws ProtocolException {
        if(command.getCommand().equals(ClientProtocol.HANDSHAKE)) {
            throw new ProtocolException("Client must not send a handshake after the initial handshake.", ServerProtocol.ERROR_GENERIC);
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
