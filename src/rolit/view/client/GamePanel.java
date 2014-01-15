package rolit.view.client;

import rolit.view.layout.GridLayoutManager;
import rolit.view.layout.HSplitLayoutManager;
import rolit.view.layout.VSplitLayoutManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new HSplitLayoutManager(200, HSplitLayoutManager.HSplitType.Right));

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(8, 8));

        JButton[][] buttons = new JButton[8][8];

        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                buttons[x][y] = new JButton("(" + x + "; " + y + ")");
                gamePanel.add(buttons[x][y]);
            }
        }

        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new VSplitLayoutManager(100, VSplitLayoutManager.VSplitType.Top));

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayoutManager(2, 4));

        scorePanel.add(new JLabel("Pieter"));
        scorePanel.add(new JLabel("0"));
        scorePanel.add(new JLabel("Martijn"));
        scorePanel.add(new JLabel("0"));
        scorePanel.add(new JLabel("Laurens"));
        scorePanel.add(new JLabel("0"));
        scorePanel.add(new JLabel("Sophie"));
        scorePanel.add(new JLabel("0"));

        sideBarPanel.add(scorePanel);
        sideBarPanel.add(new ChatPanel());

        mainPanel.add(gamePanel);
        mainPanel.add(sideBarPanel);

        add(new ServerPanel());
        add(mainPanel);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.getContentPane().add(new GamePanel());
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
