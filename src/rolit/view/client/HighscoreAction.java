package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.server.HighscorePacket;
import rolit.view.client.action.Action;

public class HighscoreAction extends Action {
    private ServerHandler currentServer;
    private String type;
    private String arg;
    private HighscorePacket result;

    public HighscoreAction(MainView.MainController mainController, ServerHandler currentServer, String type, String arg) {
        super(mainController);
        this.currentServer = currentServer;
        this.type = type;
        this.arg = arg;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.highscore(type, arg);
        result = currentServer.expectPacket(HighscorePacket.class);
        return true;
    }

    public HighscorePacket getResult() {
        return result;
    }
}
