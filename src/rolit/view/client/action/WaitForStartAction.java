package rolit.view.client.action;

import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.common.CommonProtocol;
import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketInputStream;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.GamePacket;
import rolit.model.networking.server.ServerProtocol;
import rolit.model.networking.server.StartPacket;
import rolit.view.client.MainView;

import java.io.IOException;

public class WaitForStartAction extends Action {
    private final String game;
    private final ServerHandler handler;
    private String[] players;

    public WaitForStartAction(MainView.MainController controller, ServerHandler handler, String game) {
        super(controller);
        this.handler = handler;
        this.game = game;
    }

    @Override
    public boolean doAction() throws IOException, ProtocolException {
        while(true) {
            Packet packet = handler.expectPacket(new Class[] { GamePacket.class, StartPacket.class }, false);

            if(packet instanceof StartPacket) {
                players = ((StartPacket) packet).getPlayers();
                return true;
            } else if(packet instanceof GamePacket) {
                if(((GamePacket) packet).getStatus() == ServerProtocol.STATUS_PREMATURE_LEAVE && ((GamePacket) packet).getGame().equals(game)) {
                    return false;
                }
            }
        }
    }

    public String[] getPlayers() {
        return players;
    }
}
