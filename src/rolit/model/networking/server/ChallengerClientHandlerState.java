package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;

public class ChallengerClientHandlerState extends ClientHandlerState {
    public ChallengerClientHandlerState(ClientHandler handler, String[] challenged) {
        super(handler);
    }

    @Override
    public ClientHandlerState startGame(StartGamePacket packet) {
        return new GameClientHandlerState(getHandler(), getHandler().getClientName());
    }

    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }
}