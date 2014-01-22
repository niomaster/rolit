package rolit.model.networking.server;

import rolit.model.networking.client.AuthPacket;
<<<<<<< HEAD
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.sssecurity.SSSecurity;
import rolit.util.Crypto;

import java.security.PublicKey;

=======
import rolit.util.Crypto;

>>>>>>> 36fc60f0ae5b7a5b75407da3b9e14fb26b6e9f04
public class AuthClientHandlerState extends ClientHandlerState {
    private String nonce;

    public AuthClientHandlerState(ClientHandler handler) {
        super(handler);
        nonce = Crypto.getNonce();
        getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION));
    }

    @Override
<<<<<<< HEAD
    public ClientHandlerState auth(AuthPacket packet) throws ProtocolException {
        byte[] cypherText = Crypto.base64Decode(packet.getCypherText());
        byte[] original = Crypto.base64Decode(nonce);
        PublicKey publicKey = SSSecurity.getPublicKey(getHandler().getClientName());

        if(!Crypto.verify(cypherText, original, publicKey)) {
            throw new ProtocolException("Invalid client authentication", ServerProtocol.ERROR_INVALID_LOGIN);
        }

        getHandler().write(new AuthOkPacket());
=======
    public ClientHandlerState auth(AuthPacket packet) {

>>>>>>> 36fc60f0ae5b7a5b75407da3b9e14fb26b6e9f04

        return new GameLobbyClientHandlerState(getHandler());
    }
}
