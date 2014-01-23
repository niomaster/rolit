package rolit.model.networking.server;

import rolit.model.networking.client.StartGamePacket;
import rolit.model.networking.common.ProtocolException;

/**
 * @author Pieter Bos
 */
public class WaitForGameClientHandlerState extends ClientHandlerState {
    private final String creator;

    public WaitForGameClientHandlerState(ClientHandler handler, String creator) {
        super(handler);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    /**
     * Verandert de status van de ClientHandler als het pakketje StartGame binnenkomt.
     * @param packet het ontvangen pakketje.
     * @return een nieuwe status van de ClientHandler.
     * @throws ProtocolException
     */
    @Override
    public ClientHandlerState startGame(StartGamePacket packet) throws ProtocolException {
        if(!getHandler().getClientName().equals(getCreator())) {
            throw new ProtocolException("Client tried to start a game that is not his.", ServerProtocol.ERROR_GENERIC);
        }

        getHandler().getGameByCreator(getHandler().getClientName()).start();

        return new GameClientHandlerState(getHandler(), getCreator());
    }

    /**
     * Verandert de status van de ClientHandler als er een response van een speler die gechallenged is binnenkomt.
     * @param response de reactie van de speler die gechallenged is.
     * @param challenged de speler die gechallenged is.
     * @return een nieuwe status van de ClientHandler.
     */
    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }

    @Override
    public ClientHandlerState notifyOfGameChange(ServerGame game) {
        super.notifyOfGameChange(game);

        if(game.getCreator().equals(creator)) {
            if(game.getStatus() == ServerProtocol.STATUS_PREMATURE_LEAVE) {
                getHandler().notifyCanBeChallenged();
                return new GameLobbyClientHandlerState(getHandler());
            }
        }

        return this;
    }

    @Override
    public ClientHandlerState notifyOfGameStart(String[] users) throws ProtocolException {
        getHandler().write(new StartPacket(users));
        getHandler().getGameByCreator(creator).getPlayers().get(0).getClient().notifyDoMove();
        return new GameClientHandlerState(getHandler(), creator);
    }
}
