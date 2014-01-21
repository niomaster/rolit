package rolit.model.networking.server;

import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.ChallengeResponsePacket;
import rolit.model.networking.client.CreateGamePacket;
import rolit.model.networking.client.HandshakePacket;
import rolit.model.networking.client.JoinGamePacket;
import rolit.model.networking.client.MovePacket;
import rolit.model.networking.client.StartGamePacket;
import rolit.model.networking.common.ProtocolException;

public abstract class ClientHandlerState {
    private ClientHandler handler;

    public ClientHandlerState(ClientHandler handler) {
        this.handler = handler;
    }

    private void error(String commandName) throws ProtocolException {
        throw new ProtocolException("Cannot do " + commandName + " while in state " + this.getClass().getName(), ServerProtocol.ERROR_GENERIC);
    }

    public ClientHandlerState challenge(ChallengePacket packet) throws ProtocolException {
        error("challenge");
        return null;
    }

    public ClientHandlerState challengeResponse(ChallengeResponsePacket packet) throws ProtocolException {
        error("challenge response");
        return null;
    }

    public ClientHandlerState createGame(CreateGamePacket packet) throws ProtocolException {
        error("create game");
        return null;
    }

    public ClientHandlerState handshake(HandshakePacket packet) throws ProtocolException {
        error("hello");
        return null;
    }

    public ClientHandlerState joinGame(JoinGamePacket packet) throws ProtocolException {
        error("join game");
        return null;
    }

    public ClientHandlerState move(MovePacket packet) throws ProtocolException {
        error("move");
        return null;
    }

    public ClientHandlerState startGame(StartGamePacket packet) throws ProtocolException {
        error("start game");
        return null;
    }

    public ClientHandler getHandler() {
        return handler;
    }
}
