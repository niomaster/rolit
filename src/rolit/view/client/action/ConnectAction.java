package rolit.view.client.action;

import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.HandshakePacket;
import rolit.view.client.MainView;

import javax.swing.*;
import java.io.IOException;

public class ConnectAction extends Action {
    private String hostnamePort;
    private String userName;
    private ServerHandler handler;

    public ConnectAction(String hostnamePort, String userName, MainView.MainController controller) {
        super(controller);
        this.hostnamePort = hostnamePort;
        this.userName = userName;
    }

    public String getHostnamePort() {
        return hostnamePort;
    }

    public String getUserName() {
        return userName;
    }

    public ServerHandler getServerHandler() {
        return handler;
    }

    @Override
    public boolean doAction() throws IOException, ProtocolException {
        if(!hostnamePort.contains(":")) return false;

        String hostname = hostnamePort.split(":")[0];
        int port = Integer.parseInt(hostnamePort.split(":")[1]);

        handler = new ServerHandler(hostname, port);
        handler.handshake(userName);
        handler.expectPacket(HandshakePacket.class);

        return true;
    }
}
