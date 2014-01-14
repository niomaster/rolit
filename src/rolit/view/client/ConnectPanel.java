package rolit.view.client;

import rolit.view.manager.CenterLayoutManager;
import rolit.view.manager.LeftRightLayoutManager;
import rolit.view.manager.VBoxLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pieter Bos
 *
 * Panel to connect to a server.
 */
public class ConnectPanel extends JPanel {
    private static final int DEFAULT_TEXT_FIELD_WIDTH = 200;
    private static final int DEFAULT_TEXT_FIELD_HEIGHT = 24;

    private JTextField hostname;
    private JTextField userName;
    private JButton loginButton;

    public ConnectPanel() {
        setLayout(new CenterLayoutManager());

        JPanel panel = new JPanel();
        panel.setLayout(new VBoxLayoutManager());

        JLabel logInLabel = new JLabel("Verbinden met server");
        panel.add(logInLabel);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new LeftRightLayoutManager());

        JLabel hostnameLabel = new JLabel("Hostname");
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
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame window = new JFrame();
        window.getContentPane().add(new ConnectPanel());
        window.setSize(640, 480);
        window.setMinimumSize(new Dimension(640, 480));
        window.setVisible(true);
    }
}
