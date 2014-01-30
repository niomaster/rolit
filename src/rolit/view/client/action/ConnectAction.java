package rolit.view.client.action;

import rolit.model.networking.client.AuthPacket;
import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.PacketInputStream;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.AuthOkPacket;
import rolit.model.networking.server.HandshakePacket;
import rolit.model.networking.sssecurity.SSSecurity;
import rolit.util.Crypto;
import rolit.view.client.MainView;

import javax.swing.*;
import java.io.IOException;
import java.security.PrivateKey;

public class ConnectAction extends Action {
    private String password;
    private String hostnamePort;
    private String userName;
    private ServerHandler handler;
    private PacketInputStream eventStream;
    private String error;

    public ConnectAction(String hostnamePort, String userName, String password, MainView.MainController controller) {
        super(controller);
        this.hostnamePort = hostnamePort;
        this.userName = userName;
        this.password = password;
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

    public PacketInputStream getEventStream() {
        return eventStream;
    }

    @Override
    public boolean doAction() throws IOException, ProtocolException {
        if(!hostnamePort.contains(":")) return false;

        String hostname = hostnamePort.split(":")[0];
        int port = Integer.parseInt(hostnamePort.split(":")[1]);

        handler = new ServerHandler(hostname, port);

        eventStream = handler.createPacketInputStream();

        handler.start();

        handler.handshake(userName);
        HandshakePacket packet = handler.expectPacket(HandshakePacket.class);

        if(userName.startsWith("player_")) {
            String nonce = packet.getNonce();
            PrivateKey key = SSSecurity.getPrivateKey(userName, password);

            if(key == null) {
                error = "incorrect wachtwoord of gebruikersnaam";
                return false;
            }

            String sign = Crypto.base64Encode(Crypto.sign(nonce.getBytes(), key));
            handler.auth(sign);
            handler.expectPacket(AuthOkPacket.class);
        }

        return true;
    }

    public String getError() {
        return error;
    }
}
