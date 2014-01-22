package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;

public class ChallengerClientHandlerState extends ClientHandlerState {
    public ChallengerClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState startGame(StartGamePacket packet) {
        return new GameClientHandlerState(getHandler(), getHandler().getClientName());
    }
}
