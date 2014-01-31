package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.client.StartGamePacket;
import rolit.model.networking.common.Packet;
import rolit.model.networking.server.ChallengeResponsePacket;
import rolit.view.client.action.Action;

public class WaitForChallengeResponseOrStartAction extends Action {
    private ServerHandler currentServer;
    private Packet packet;

    public WaitForChallengeResponseOrStartAction(MainView.MainController mainController, ServerHandler currentServer) {
        super(mainController);
        this.currentServer = currentServer;
    }

    @Override
    public boolean doAction() throws Throwable {
        packet = currentServer.expectPacket(new Class[] { ChallengeResponsePacket.class, StartGamePacket.class }, true);
        return true;
    }

    public Packet getPacket() {
        return packet;
    }
}
