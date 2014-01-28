package rolit.view.client;

import rolit.view.layout.CenterLayoutManager;
import rolit.view.layout.LeftRightLayoutManager;
import rolit.view.layout.VBoxLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Pieter Bos
 *
 * Panel to connect to a server.
 */
public class ConnectPanel extends JPanel {
    private static final int DEFAULT_TEXT_FIELD_WIDTH = 200;
    private static final int DEFAULT_TEXT_FIELD_HEIGHT = 24;
    private final ConnectPanelController controller;

    private JTextField hostname;
    private JTextField userName;
    private JButton loginButton;

    public class ConnectPanelController implements ActionListener {
        private final ConnectPanel panel;
        private final MainView.MainController mainController;

        public ConnectPanelController(ConnectPanel panel, MainView.MainController mainController) {
            this.panel = panel;
            this.mainController = mainController;
        }

        public void initialize() {
            panel.getLoginButton().addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == panel.getLoginButton()) {
                mainController.doConnect(panel.getHostnameTextField().getText(), panel.getUserNameTextField().getText());
            }
        }

        public void disable() {
            panel.getLoginButton().setEnabled(false);
        }

        public void enable() {
            panel.getLoginButton().setEnabled(true);
        }
    }

    public ConnectPanel(MainView.MainController mainController) {
        this.controller = new ConnectPanelController(this, mainController);

        setLayout(new CenterLayoutManager());

        JPanel panel = new JPanel();
        panel.setLayout(new VBoxLayoutManager());

        JLabel logInLabel = new JLabel("Verbinden met server");
        panel.add(logInLabel);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new LeftRightLayoutManager());

        JLabel hostnameLabel = new JLabel("Hostname:poort");
        hostname = new JTextField();
        hostname.setPreferredSize(new Dimension(DEFAULT_TEXT_FIELD_WIDTH, DEFAULT_TEXT_FIELD_HEIGHT));
        panel1.add(hostnameLabel);
        panel1.add(hostname);
        panel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new LeftRightLayoutManager());

        JLabel userNameLabel = new JLabel("Gebruikersnaam");
        userName = new JTextField();
        userName.setPreferredSize(new Dimension(DEFAULT_TEXT_FIELD_WIDTH, DEFAULT_TEXT_FIELD_HEIGHT));
        panel2.add(userNameLabel);
        panel2.add(userName);
        panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new LeftRightLayoutManager());


        loginButton = new JButton("Inloggen");
        panel3.add(Box.createGlue());
        panel3.add(loginButton);
        panel.add(panel3);

        add(panel);

        controller.initialize();
    }

    public ConnectPanelController getController() {
        return controller;
    }

    public JTextField getHostnameTextField() {
        return hostname;
    }

    public JTextField getUserNameTextField() {
        return userName;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
