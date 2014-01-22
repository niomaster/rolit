package rolit.model.networking.server;

import rolit.model.networking.client.AuthPacket;
import rolit.util.Crypto;

public class AuthClientHandlerState extends ClientHandlerState {
    private String nonce;

    public AuthClientHandlerState(ClientHandler handler) {
        super(handler);
        nonce = Crypto.getNonce();
        getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION));
    }

    @Override
    public ClientHandlerState auth(AuthPacket packet) {


        return new GameLobbyClientHandlerState(getHandler());
    }
}
