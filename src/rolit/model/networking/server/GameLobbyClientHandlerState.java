package rolit.model.networking.server;

import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.CreateGamePacket;
import rolit.model.networking.client.JoinGamePacket;

public class GameLobbyClientHandlerState extends ClientHandlerState {
    public GameLobbyClientHandlerState(ClientHandler handler) {
        super(handler);
        getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION));
    }

    @Override
    public ClientHandlerState challenge(ChallengePacket packet) {
        return new ChallengerClientHandlerState(getHandler());
    }

    @Override
    public ClientHandlerState joinGame(JoinGamePacket packet) {
        return new WaitForGameClientHandlerState(getHandler(), packet.getCreator());
    }

    @Override
    public ClientHandlerState createGame(CreateGamePacket packet) {
        return new WaitForGameClientHandlerState(getHandler(), getHandler().getClientName());
    }
}
