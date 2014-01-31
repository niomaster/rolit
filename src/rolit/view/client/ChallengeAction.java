package rolit.view.client;

import rolit.model.networking.client.ServerHandler;
import rolit.view.client.action.Action;

public class ChallengeAction extends Action {
    private ServerHandler currentServer;
    private String[] challenged;

    public ChallengeAction(MainView.MainController mainController, ServerHandler currentServer, String[] challenged) {
        super(mainController);
        this.currentServer = currentServer;
        this.challenged = challenged;
    }

    public String[] getChallenged() {
        return challenged;
    }

    @Override
    public boolean doAction() {
        currentServer.challenge(challenged);
        return true;
    }
}
