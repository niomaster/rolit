package rolit.view.client;

import rolit.view.layout.HBoxLayoutManager;
import rolit.view.layout.LeftRightLayoutManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerPanel extends JPanel {
    private final ServerController controller;
    JLabel serverConnectionLabel;
    JLabel userNameLabel;
    JButton logoutButton;

    public class ServerController implements ActionListener {
        private ServerPanel panel;
        private MainView.MainController controller;

        public ServerController(ServerPanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void initialize() {
            panel.getLogoutButton().addActionListener(this);
        }

        public void update() {
            panel.getServerConnectionLabel().setText("Verbonden met " + controller.getCurrentServer().getHostName());
            panel.getUserNameLabel().setText("Ingelogd als " + controller.getCurrentUser());
        }

        public void enable() {
            panel.getLogoutButton().setEnabled(true);
        }

        public void disable() {
            panel.getLogoutButton().setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.doDisconnect();
        }
    }

    public ServerPanel(MainView.MainController controller) {
        this.controller = new ServerController(this, controller);
        setLayout(new LeftRightLayoutManager());

        serverConnectionLabel = new JLabel("Verbonden met ");
        add(serverConnectionLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new HBoxLayoutManager());

        userNameLabel = new JLabel("Ingelogd als ");
        logoutButton = new JButton("Uitloggen");

        rightPanel.add(userNameLabel);
        rightPanel.add(logoutButton);

        add(rightPanel);
        this.controller.initialize();
    }

    public JLabel getServerConnectionLabel() {
        return serverConnectionLabel;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public ServerController getController() {
        return controller;
    }
}
