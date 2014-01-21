package rolit.model.networking.server;

public class AuthClientHandlerState extends ClientHandlerState {
    public AuthClientHandlerState(ClientHandler handler) {
        super(handler);
        getHandler().write(new HandshakePacket(Server.GLOBAL_SUPPORTS, Server.GLOBAL_VERSION, "hallo"));
    }
}
