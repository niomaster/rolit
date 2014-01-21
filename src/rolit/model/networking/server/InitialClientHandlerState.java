package rolit.model.networking.server;

import rolit.model.networking.client.ClientProtocol;
import rolit.model.networking.client.HandshakePacket;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;

public class InitialClientHandlerState extends ClientHandlerState {
    public InitialClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState handshake(HandshakePacket packet) throws ProtocolException {
        if(packet.getSupports() < CommonProtocol.SUPPORTS_BAREBONE || packet.getSupports() > CommonProtocol.SUPPORTS_CHAT_CHALLENGE) {
            throw new ProtocolException("Invalid supports integer", ServerProtocol.ERROR_GENERIC);
        }

        if(packet.getName().length() < 2 || packet.getName().length() > 32) {
            throw new ProtocolException("Invalid name length or format", ServerProtocol.ERROR_INVALID_LOGIN);
        }

        getHandler().setClientName(packet.getName());
        getHandler().setClientSupports(packet.getSupports());

        if(packet.getName().startsWith("player_")) {
            return new AuthClientHandlerState(getHandler());
        } else {
            return new GameLobbyClientHandlerState(getHandler());
        }
    }
}
