package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

public class StartAction extends Action {
    private ServerHandler currentServer;

    public StartAction(MainView.MainController mainController, ServerHandler currentServer) {
        super(mainController);
        this.currentServer = currentServer;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.startGame();

        return true;
    }
}
