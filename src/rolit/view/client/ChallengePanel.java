package rolit.view.client;

import rolit.view.manager.CenterLayoutManager;
import rolit.view.manager.GridLayoutManager;
import rolit.view.manager.HBoxLayoutManager;
import rolit.view.manager.VBoxLayoutManager;

import javax.swing.*;
import java.awt.*;

public class ChallengePanel extends JPanel {
    public ChallengePanel() {
        setLayout(new CenterLayoutManager());
        setSize(new Dimension(300, 1));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new VBoxLayoutManager());

        JLabel challengeLabel = new JLabel("Daag mensen uit:");

        JPanel challengePanel = new JPanel();
        challengePanel.setLayout(new HBoxLayoutManager());

        JComboBox players = new JComboBox<String>();
        players.addItem("Pieter");
        players.addItem("Martijn");

        JButton challengeButton = new JButton("Uitdagen");

        challengePanel.add(players);
        challengePanel.add(challengeButton);

        JLabel challengedPeople = new JLabel("Uitgedaagde mensen:");

        JPanel challengedPanel = new JPanel();
        challengedPanel.setLayout(new GridLayoutManager(2, 2));
        challengedPanel.add(new JLabel("Laurens"));
        challengedPanel.add(new JButton("Verwijder"));
        challengedPanel.add(new JLabel("Sophie"));
        challengedPanel.add(new JButton("Verwijder"));

        mainPanel.add(challengeLabel);
        mainPanel.add(challengePanel);
        mainPanel.add(Box.createGlue());
        mainPanel.add(challengedPeople);
        mainPanel.add(challengedPanel);

        add(mainPanel);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ChallengePanel());
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
