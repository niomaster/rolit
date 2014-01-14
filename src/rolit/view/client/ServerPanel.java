package rolit.view.client;

import rolit.view.manager.HBoxLayoutManager;
import rolit.view.manager.LeftRightLayoutManager;

import javax.swing.*;

public class ServerPanel extends JPanel {
    JLabel serverConnectionLabel;
    JLabel userNameLabel;
    JButton logoutButton;

    public ServerPanel() {
        setLayout(new LeftRightLayoutManager());

        serverConnectionLabel = new JLabel("Verbonden met pieterbos.me:667");
        add(serverConnectionLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new HBoxLayoutManager());

        userNameLabel = new JLabel("Ingelogd als Pieter");
        logoutButton = new JButton("Uitloggen");

        rightPanel.add(userNameLabel);
        rightPanel.add(logoutButton);

        add(rightPanel);
    }
}
