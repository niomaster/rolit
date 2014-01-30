package rolit.view.client.action;

import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.common.Packet;
import rolit.model.networking.common.PacketInputStream;
import rolit.model.networking.common.ProtocolException;
import rolit.model.networking.server.GameOverPacket;
import rolit.model.networking.server.MoveDonePacket;
import rolit.model.networking.server.MovePacket;
import rolit.util.Arrays;
import rolit.view.client.MainView;

import java.io.IOException;

public class WaitForGameEventAction extends Action {
    private static final Class[] EVENT_TYPES = {
            MovePacket.class,
            MoveDonePacket.class,
            GameOverPacket.class
    };
    private Packet eventPacket;
    private ServerHandler serverHandler;

    public WaitForGameEventAction(MainView.MainController controller, ServerHandler serverHandler) {
        super(controller);
        this.serverHandler = serverHandler;
    }

    @Override
    public boolean doAction() throws IOException, ProtocolException {
        eventPacket = serverHandler.expectPacket(EVENT_TYPES, true);
        return true;
    }

    public Packet getEventPacket() {
        return eventPacket;
    }
}
