package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

import java.io.IOException;

public class DisconnectAction extends Action {
    private ServerHandler currentServer;

    public DisconnectAction(MainView.MainController mainController, ServerHandler currentServer) {
        super(mainController);
        this.currentServer = currentServer;
    }

    @Override
    public boolean doAction() throws IOException {
        currentServer.disconnect();
        return true;
    }
}
