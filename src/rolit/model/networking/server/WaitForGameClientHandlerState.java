package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;

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
    public ClientHandlerState startGame(StartGamePacket packet) {
        return new GameClientHandlerState(getHandler(), getCreator());
    }
}
