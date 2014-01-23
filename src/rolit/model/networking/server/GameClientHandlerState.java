package rolit.model.networking.server;

import rolit.model.networking.common.ProtocolException;

/**
 * De ClientHandler ten aanzien van de status van de game.
 * @author Pieter Bos
 */
public class GameClientHandlerState extends ClientHandlerState {
    private final String creator;
    private final ServerGame game;

    public GameClientHandlerState(ClientHandler handler, String creator) {
        super(handler);
        this.creator = creator;
        this.game = getHandler().getGameByCreator(creator);
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
    public ClientHandlerState move(rolit.model.networking.client.MovePacket packet) throws ProtocolException {
        int index = game.getIndex(getHandler().getUser());

        game.doMove(packet.getX(), packet.getY());

        getHandler().notifyMove(creator, packet.getX(), packet.getY());

        game.getPlayers().get((index + 1) % game.getPlayerCount()).getClient().notifyDoMove();

        return this;
    }

    @Override
    public ClientHandlerState notifyDoMove() {
        getHandler().write(new rolit.model.networking.server.MovePacket());
        return this;
    }

    @Override
    public ClientHandlerState notifyOfMove(String mover, int x, int y) {
        getHandler().write(new MoveDonePacket(mover, x, y));
        return this;
    }
}
