package rolit.model.networking.server;

import rolit.model.networking.client.HandshakePacket;

public class InitialClientHandlerState extends ClientHandlerState {
    public InitialClientHandlerState(ClientHandler handler) {
        super(handler);
    }

    @Override
    public ClientHandlerState handshake(HandshakePacket packet) {
        

        return this;
    }
}
