package rolit.model.networking.server;

import rolit.model.networking.client.ClientProtocol;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.ProtocolException;
import rolit.util.Crypto;

public class InitialClientHandlerState extends ClientHandlerState {
    public InitialClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState handshake(rolit.model.networking.client.HandshakePacket packet) throws ProtocolException {
        if(packet.getSupports() < CommonProtocol.SUPPORTS_BAREBONE || packet.getSupports() > CommonProtocol.SUPPORTS_CHAT_CHALLENGE) {
            throw new ProtocolException("Invalid supports integer", ServerProtocol.ERROR_GENERIC);
        }

        if(packet.getName().length() < 2 || packet.getName().length() > 32) {
            throw new ProtocolException("Invalid name length or format", ServerProtocol.ERROR_INVALID_LOGIN);
        }

        getHandler().setClientSupports(packet.getSupports());

        if(packet.getName().startsWith("player_")) {
            String nonce = Crypto.getNonce();
            getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION, nonce));
            return new AuthClientHandlerState(getHandler(), nonce, packet.getName());
        } else {
            getHandler().setClientName(packet.getName());
            getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION));
            getHandler().writeInfo();
            getHandler().notifyOnline();
            getHandler().notifyCanBeChallenged();
            return new GameLobbyClientHandlerState(getHandler());
        }
    }

    @Override
    public ClientHandlerState exit() {
        return null;
    }
}
