package rolit.model.networking.server;

import rolit.model.networking.client.AuthPacket;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.sssecurity.SSSecurity;
import rolit.util.Crypto;

import java.security.PublicKey;

import rolit.util.Crypto;

/**
 * De staat van ClientHandler ten aanzien van het authenticeren.
 * @author Pieter Bos
 */
public class AuthClientHandlerState extends ClientHandlerState {
    private String nonce;
    private String clientName;

    public AuthClientHandlerState(ClientHandler handler, String nonce, String clientName) {
        super(handler);
        this.nonce = nonce;
        this.clientName = clientName;
    }

    /**
     * Verandert de status van de ClientHandler als de speler geauthenticeerd wordt.
     * @param packet het pakket waarin de cypher zit die gecontroleerd moet worden.
     * @return een nieuwe ClientState met een nieuwe status.
     * @throws ProtocolException als de speler niet geauthenticeerd kan worden, wordt er een exception gegegooid.
     */
    @Override
    public ClientHandlerState auth(AuthPacket packet) throws ProtocolException {
        byte[] cypherText = Crypto.base64Decode(packet.getCypherText());
        byte[] original = nonce.getBytes();
        PublicKey publicKey = SSSecurity.getPublicKey(clientName);

        if(!Crypto.verify(cypherText, original, publicKey)) {
            throw new ProtocolException("Invalid client authentication", ServerProtocol.ERROR_INVALID_LOGIN);
        }

        getHandler().write(new AuthOkPacket());

        getHandler().setClientName(this.clientName);

        return new GameLobbyClientHandlerState(getHandler());
    }

    @Override
    public ClientHandlerState exit() {
        return null;
    }
}
