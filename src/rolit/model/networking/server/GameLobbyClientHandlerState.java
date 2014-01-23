package rolit.model.networking.server;

import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.CreateGamePacket;
import rolit.model.networking.client.JoinGamePacket;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;

/**
 * De ClientHandler status ten aanzien van de lobby.
 * @author Pieter Bos
 */
public class GameLobbyClientHandlerState extends ClientHandlerState {
    public GameLobbyClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    /**
     * Verandert de status van de speler die challenged naar challenger.
     * @param packet het pakketje dat ontvangen is.
     * @return een nieuwe status voor de challenger.
     * @throws ProtocolException als er iets fout gaat wordt er een exceptie gegooid.
     */
    @Override
    public ClientHandlerState challenge(ChallengePacket packet) throws ProtocolException {
        getHandler().notifyChallenged(packet.getChallenged());
        getHandler().notifyCannotBeChallenged();
        return new ChallengerClientHandlerState(getHandler(), packet.getChallenged());
    }

    /**
     * Verandert de status van een client als de speler een spel joint.
     * @param packet het JoinGame pakketje dat van de client ontvangen wordt.
     * @return een nieuwe status van de client.
     * @throws ProtocolException als er iets fout gaat wordt er een exceptie gegooid.
     */
    @Override
    public ClientHandlerState joinGame(JoinGamePacket packet) throws ProtocolException {
        ServerGame game = getHandler().getGameByCreator(packet.getCreator());

        if(game == null) {
            throw new ProtocolException("Client tried to join a game that does not exist", ServerProtocol.ERROR_NO_SUCH_GAME);
        }

        if(game.getPlayerCount() >= CommonProtocol.MAXIMUM_PLAYER_COUNT) {
            throw new ProtocolException("Client tried to join a game that is already full", ServerProtocol.ERROR_GAME_FULL);
        }

        game.addPlayer(getHandler().getUser());

        getHandler().notifyCannotBeChallenged();

        return new WaitForGameClientHandlerState(getHandler(), packet.getCreator());
    }

    /**
     * Als de speler een game maakt wordt de speler in een wachtende status gezet.
     * @param packet het CreateGame pakktje ontvangen van de client.
     * @return een nieuwe status van de client.
     */
    @Override
    public ClientHandlerState createGame(CreateGamePacket packet) throws ProtocolException {
        getHandler().createGame();
        getHandler().notifyCannotBeChallenged();
        return new WaitForGameClientHandlerState(getHandler(), getHandler().getClientName());
    }

    /**
     * Verandert de status van een client als hij gechallenged wordt.
     * @param challenger de speler die challenged.
     * @param others de andere spelers die gechallenged zijn.
     * @return
     */
    @Override
    public ClientHandlerState notifyChallengedBy(String challenger, String[] others) {
        getHandler().write(new rolit.model.networking.server.ChallengePacket(challenger, others));
        getHandler().notifyCannotBeChallenged();
        return new ChallengedClientHandlerState(getHandler(), challenger, others);
    }

    /**
     * Verandert de status van de speler die gechallenged is na zijn reactie op de challenge.
     * @param response de reactie van de speler op de challenge.
     * @param challenged de speler.
     * @return een nieuwe status van de client.
     */
    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }

    @Override
    public ClientHandlerState exit() {
        return null;
    }

    /**
     * De enige staat waarin een client gechallenged kan worden.
     * @return true
     */
    @Override
    public boolean canBeChallenged() {
        return true;
    }
}
