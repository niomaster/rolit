package rolit.model.networking.server;

import rolit.model.networking.client.*;
import rolit.model.networking.common.ProtocolException;

/**
 * De status van de ClientHandler.
 * @author Pieter Bos
 */
public abstract class ClientHandlerState {
    private ClientHandler handler;

    public ClientHandlerState(ClientHandler handler) {
        this.handler = handler;
    }

    /**
     * Gooit een exceptie als er niet van status verandert kan worden.
     * @param commandName naam van het commanda.
     * @throws ProtocolException wordt gegooid van een status niet naar de andere status verandert kan worden.
     */
    private void error(String commandName) throws ProtocolException {
        throw new ProtocolException("Cannot do " + commandName + " while in state " + this.getClass().getName(), ServerProtocol.ERROR_GENERIC);
    }

    public ClientHandlerState challenge(rolit.model.networking.client.ChallengePacket packet) throws ProtocolException {
        error("challenge");
        return null;
    }

    public ClientHandlerState challengeResponse(rolit.model.networking.client.ChallengeResponsePacket packet) throws ProtocolException {
        error("challenge response");
        return null;
    }

    public ClientHandlerState createGame(rolit.model.networking.client.CreateGamePacket packet) throws ProtocolException {
        error("create game");
        return null;
    }

    public ClientHandlerState handshake(rolit.model.networking.client.HandshakePacket packet) throws ProtocolException {
        error("hello");
        return null;
    }

    public ClientHandlerState joinGame(rolit.model.networking.client.JoinGamePacket packet) throws ProtocolException {
        error("join game");
        return null;
    }

    public ClientHandlerState move(rolit.model.networking.client.MovePacket packet) throws ProtocolException {
        error("move");
        return null;
    }

    public ClientHandlerState startGame(rolit.model.networking.client.StartGamePacket packet) throws ProtocolException {
        error("start game");
        return null;
    }

    public ClientHandler getHandler() {
        return handler;
    }

    public ClientHandlerState auth(rolit.model.networking.client.AuthPacket packet) throws ProtocolException {
        error("auth");
        return null;
    }

    public ClientHandlerState notifyChallengedBy(String challenger, String[] others) throws ProtocolException {
        error("challenged");
        return null;
    }

    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) throws ProtocolException {
        error("challenge response");
        return null;
    }

    /**
     * Standaardgedrag is om gewoon het pakket door te sturen, tenzij de staat van het spel is veranderd terwijl het
     * spel al gestart is.
     * @param game het desbetreffende spel
     * @return zichzelf, aangezien de staat niet verandert.
     */
    public ClientHandlerState notifyOfGameChange(ServerGame game) {
        if(!(game.isStarted() && game.isStopped())) {
            getHandler().write(new GamePacket(game.getCreator().getUsername(), game.getStatus(), game.getPlayerCount()));
        }

        return this;
    }

    public ClientHandlerState exit() {
        // TODO add to logging service
        System.out.println("Warning: exit not implemented in state " + this.getClass().getName());
        return null;
    }

    /**
     * Wordt gebruikt om de initiele lijst met supportsChallenge's te sturen naar een nieuwe client.
     * @return false
     */
    public boolean canBeChallenged() {
        return false;
    }

    public ClientHandlerState notifyOfGameStart(String[] users) throws ProtocolException {
        error("game start by other");
        return null;
    }

    public ClientHandlerState notifyDoMove() throws ProtocolException {
        error("notify of move");
        return null;
    }

    public ClientHandlerState notifyOfMove(String mover, int x, int y) throws ProtocolException {
        error("notify of move by other");
        return null;
    }
}
