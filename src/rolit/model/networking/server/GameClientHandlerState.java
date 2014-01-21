package rolit.model.networking.server;

import rolit.model.networking.client.MovePacket;

public class GameClientHandlerState extends ClientHandlerState {
    private final String creator;

    public GameClientHandlerState(ClientHandler handler, String creator) {
        super(handler);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public ClientHandlerState move(MovePacket packet) {
        return this;
    }
}
