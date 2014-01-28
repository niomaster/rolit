package rolit.view.client.action;

import rolit.view.client.MainView;

public abstract class Action extends Thread {
    private final MainView.MainController controller;

    public Action(MainView.MainController controller) {
        this.controller = controller;
    }

    public abstract boolean doAction() throws Throwable;

    @Override
    public void run() {
        try {
            if(doAction()) {
                controller.actionSucceeded();
            } else {
                controller.actionFailed();
            }
        } catch (Throwable throwable) {
            // Een actie móet altijd falen of goed gaan.
            controller.actionFailed();
        }
    }
}
