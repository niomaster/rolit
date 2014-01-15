package rolit.view.client;

import rolit.view.manager.VBoxLayoutManager;
import rolit.view.manager.VSplitLayoutManager;

import javax.swing.*;
import java.awt.*;

public class WaitPanel extends JPanel {
    public WaitPanel() {
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new VBoxLayoutManager());

        JLabel status = new JLabel("Het spel van Pieter is nog niet begonnen. Er zitten nu 3 mensen in het spel.");
        JButton actionButton = new JButton("Beginnen");
        actionButton.setEnabled(false);

        mainPanel.add(status);
        mainPanel.add(actionButton);

        add(new ServerPanel());
        add(mainPanel);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.getContentPane().add(new WaitPanel());
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
