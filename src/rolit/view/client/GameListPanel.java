package rolit.view.client;

import rolit.view.layout.*;

import javax.swing.*;
import java.awt.*;

public class GameListPanel extends JPanel {
    public GameListPanel() {
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new HSplitLayoutManager(200, HSplitLayoutManager.HSplitType.Right));

        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Bottom));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayoutManager(3, 3));

        listPanel.add(new JLabel("Spelmaker"));
        listPanel.add(new JLabel("Aantal spelers"));
        listPanel.add(Box.createGlue());
        listPanel.add(new JLabel("Pieter"));
        listPanel.add(new JLabel("2"));
        listPanel.add(new JButton("Meedoen"));
        listPanel.add(new JLabel("Martijn"));
        listPanel.add(new JLabel("4"));
        JButton joinGameButton = new JButton("Meedoen");
        joinGameButton.setEnabled(false);
        listPanel.add(joinGameButton);


        JPanel buttonsArrayPanel = new JPanel();
        buttonsArrayPanel.setLayout(new HBoxLayoutManager());

        JButton createGameButton = new JButton("Maak spel");
        JButton challengeButton = new JButton("Daag een speler uit");

        buttonsArrayPanel.add(createGameButton);
        buttonsArrayPanel.add(challengeButton);

        gamesPanel.add(listPanel);
        gamesPanel.add(buttonsArrayPanel);

        mainPanel.add(gamesPanel);
        mainPanel.add(new ChatPanel());

        add(new ServerPanel());
        add(mainPanel);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.getContentPane().add(new GameListPanel());
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
