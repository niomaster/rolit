package rolit.model.networking.server;

import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.CreateGamePacket;
import rolit.model.networking.client.JoinGamePacket;

public class GameLobbyClientHandlerState extends ClientHandlerState {
    public GameLobbyClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState challenge(ChallengePacket packet) {
        return new ChallengerClientHandlerState(getHandler());
    }

    @Override
    public ClientHandlerState joinGame(JoinGamePacket packet) {
        return new GameClientHandlerState(getHandler(), packet.getCreator());
    }

    @Override
    public ClientHandlerState createGame(CreateGamePacket packet) {
        return new GameClientHandlerState(getHandler(), getHandler().);
    }
}
