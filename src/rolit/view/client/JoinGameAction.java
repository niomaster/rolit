package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

public class JoinGameAction extends Action {
    private ServerHandler currentServer;
    private String creator;

    public JoinGameAction(MainView.MainController mainController, ServerHandler currentServer, String creator) {
        super(mainController);
        this.currentServer = currentServer;
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.joinGame(creator);
        return true;
    }
}
