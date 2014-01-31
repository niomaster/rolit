package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

public class MessageAction extends Action {
    private ServerHandler currentServer;
    private String text;

    public MessageAction(MainView.MainController mainController, ServerHandler currentServer, String text) {
        super(mainController);
        this.currentServer = currentServer;
        this.text = text;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.message(text);
        return true;
    }
}
