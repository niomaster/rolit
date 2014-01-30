package rolit.view.client.action;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.MainView;

public class MoveAction extends Action {
    private ServerHandler currentServer;
    private int x;
    private int y;

    public MoveAction(MainView.MainController mainController, ServerHandler currentServer, int x, int y) {
        super(mainController);
        this.currentServer = currentServer;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.move(x, y);
        return true;
    }
}
