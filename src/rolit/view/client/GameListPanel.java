package rolit.view.client;

import rolit.view.manager.VSplitLayoutManager;

import javax.swing.*;
import java.awt.*;

public class GameListPanel extends JPanel {
    public GameListPanel() {
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));
        add(new ServerPanel());
        add(Box.createGlue());
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
