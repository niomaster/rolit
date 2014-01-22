package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;
import rolit.model.networking.common.ProtocolException;

public class WaitForGameClientHandlerState extends ClientHandlerState {
    private final String creator;

    public WaitForGameClientHandlerState(ClientHandler handler, String creator) {
        super(handler);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public ClientHandlerState startGame(StartGamePacket packet) throws ProtocolException {
        if(!getHandler().getClientName().equals(getCreator())) {
            throw new ProtocolException("Client tried to start a game that is not his.", ServerProtocol.ERROR_GENERIC);
        }

        return new GameClientHandlerState(getHandler(), getCreator());
    }

    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }
}
