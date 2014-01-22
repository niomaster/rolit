package rolit.model.networking.server;

import rolit.model.networking.client.ChallengePacket;
import rolit.model.networking.client.CreateGamePacket;
import rolit.model.networking.client.JoinGamePacket;
import rolit.model.networking.common.ProtocolException;

public class GameLobbyClientHandlerState extends ClientHandlerState {
    public GameLobbyClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState challenge(ChallengePacket packet) throws ProtocolException {
        getHandler().notifyChallenged(packet.getChallenged());
        return new ChallengerClientHandlerState(getHandler(), packet.getChallenged());
    }

    @Override
    public ClientHandlerState joinGame(JoinGamePacket packet) throws ProtocolException {
        ServerGame game = getHandler().getGameByCreator(packet.getCreator());

        if(game == null) {
            throw new ProtocolException("Client tried to join a game that does not exist", ServerProtocol.ERROR_NO_SUCH_GAME);
        }

        if(game.getPlayerCount() )

        return new WaitForGameClientHandlerState(getHandler(), packet.getCreator());
    }

    @Override
    public ClientHandlerState createGame(CreateGamePacket packet) {
        return new WaitForGameClientHandlerState(getHandler(), getHandler().getClientName());
    }

    @Override
    public ClientHandlerState notifyChallengedBy(String challenger, String[] others) {
        getHandler().write(new rolit.model.networking.server.ChallengePacket(challenger, others));
        return new ChallengedClientHandlerState(getHandler(), challenger, others);
    }

    @Override
    public ClientHandlerState notifyChallengeResponseBy(boolean response, String challenged) {
        getHandler().write(new ChallengeResponsePacket(challenged, response));
        return this;
    }
}
