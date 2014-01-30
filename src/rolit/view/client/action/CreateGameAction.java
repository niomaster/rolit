package rolit.view.client.action;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.MainView;

public class CreateGameAction extends Action {
    private ServerHandler server;

    public CreateGameAction(MainView.MainController controller, ServerHandler server) {
        super(controller);
        this.server = server;
    }

    @Override
    public boolean doAction() {
        server.createGame();
        return true;
    }
}
