package rolit.model.networking.server;

import rolit.model.networking.client.MovePacket;

/**
 * De ClientHandler ten aanzien van de status van de game.
 * @author Pieter Bos
 */
public class GameClientHandlerState extends ClientHandlerState {
    private final String creator;

    public GameClientHandlerState(ClientHandler handler, String creator) {
        super(handler);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * Verandert de status van de game
     * @param packet het pakketje met de zet van de speler.
     * @return een nieuwe status van de game.
     */
    @Override
    public ClientHandlerState move(rolit.model.networking.client.MovePacket packet) {
        return this;
    }
}
