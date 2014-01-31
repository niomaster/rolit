package rolit.model.event;

import rolit.model.networking.server.ClientHandler;

public interface ServerListener {
    public void serverError(String reason);
    public void newClient(ClientHandler handler);
    public void clientError(String reason);
    public void clientMessage(String clientName, String text);
}
