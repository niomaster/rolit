package rolit.model.networking.server;

import rolit.model.networking.client.AuthPacket;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.sssecurity.SSSecurity;
import rolit.util.Crypto;

import java.security.PublicKey;

import rolit.util.Crypto;

public class AuthClientHandlerState extends ClientHandlerState {
    private String nonce;

    public AuthClientHandlerState(ClientHandler handler) {
        super(handler);
        nonce = Crypto.getNonce();
        getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION));
    }

    @Override
    public ClientHandlerState auth(AuthPacket packet) throws ProtocolException {
        byte[] cypherText = Crypto.base64Decode(packet.getCypherText());
        byte[] original = Crypto.base64Decode(nonce);
        PublicKey publicKey = SSSecurity.getPublicKey(getHandler().getClientName());

        if(!Crypto.verify(cypherText, original, publicKey)) {
            throw new ProtocolException("Invalid client authentication", ServerProtocol.ERROR_INVALID_LOGIN);
        }

        getHandler().write(new AuthOkPacket());

        return new GameLobbyClientHandlerState(getHandler());
    }
}
