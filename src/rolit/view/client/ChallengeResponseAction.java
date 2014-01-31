package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

public class ChallengeResponseAction extends Action {
    private final ServerHandler currentServer;
    private final boolean accept;

    public ChallengeResponseAction(MainView.MainController mainController, ServerHandler currentServer, boolean accept) {
        super(mainController);
        this.currentServer = currentServer;
        this.accept = accept;
    }

    public boolean isAccept() {
        return accept;
    }

    @Override
    public boolean doAction() throws Throwable {
        currentServer.challengeResponse(accept);
        return true;
    }
}
