package rolit.view.client;

import rolit.view.client.action.ConnectAction;
import rolit.view.client.action.Action;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final MainController controller;

    /**
     * Zorgt er voor dat de view de goede panel laat zien en dat de juiste acties op de achtergrond gebeuren.
     */
    public class MainController {
        private final MainView view;

        private final ConnectPanel connectPanel;

        private Action currentAction;

        public MainController(MainView view) {
            this.view = view;
            this.connectPanel = new ConnectPanel(this);
        }

        public void initialize() {
            view.add(connectPanel);
            view.setVisible(true);
        }

        private void doAction(Action action) {
            currentAction = action;
            currentAction.start();
        }

        public void doConnect(String hostnamePort, String userName) {
            connectPanel.getController().disable();
            doAction(new ConnectAction(hostnamePort, userName, this));
        }

        public synchronized void actionSucceeded() {
            if(currentAction instanceof ConnectAction) {
                ConnectAction action = (ConnectAction) currentAction;
                error("Nu verbonden met " + action.getHostnamePort());
            }

            currentAction = null;
        }

        public synchronized void actionFailed() {
            if(currentAction instanceof ConnectAction) {
                ConnectAction action = (ConnectAction) currentAction;
                error("Kon niet verbinden met " + action.getHostnamePort());
                connectPanel.getController().enable();
            }

            currentAction = null;
        }
    }

    public MainView() {
        this.controller = new MainController(this);

        setSize(640, 480);
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controller.initialize();
    }

    public static void error(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
