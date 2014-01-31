package rolit.view.client;

import rolit.view.layout.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ChallengePanel extends JPanel {
    private final ServerPanel serverPanel;
    private final ChallengeController controller;
    private final JComboBox<String> players;
    private final JButton challengeButton;
    private final JPanel challengedPanel;
    private final JButton challengeAllButton;

    public ChallengeController getController() {
        return controller;
    }

    public JComboBox<String> getPlayers() {
        return players;
    }

    public JButton getChallengeButton() {
        return challengeButton;
    }

    public JPanel getChallengedPanel() {
        return challengedPanel;
    }

    public ServerPanel getServerPanel() {
        return serverPanel;
    }

    public JButton getChallengeAllButton() {
        return challengeAllButton;
    }

    public class ChallengeController implements ActionListener {
        private ChallengePanel panel;
        private MainView.MainController controller;
        private LinkedList<String> challenged = new LinkedList<String>();

        public ChallengeController(ChallengePanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void updateCanBeChallenged() {
            LinkedList<String> toRemove = new LinkedList<String>();

            for(String challenged : this.challenged) {
                if(!controller.getCanBeChallenged().contains(challenged)) {
                    toRemove.add(challenged);
                }
            }

            this.challenged.removeAll(toRemove);

            update();
        }

        public void update() {
            panel.getServerPanel().getController().update();

            panel.getPlayers().removeAllItems();

            for (String challenged : controller.getCanBeChallenged()) {
                if (!this.challenged.contains(challenged)) {
                    panel.getPlayers().addItem(challenged);
                }
            }

            panel.getChallengedPanel().removeAll();
            panel.getChallengedPanel().setLayout(null);

            for (String challenged : this.challenged) {
                panel.getChallengedPanel().add(new JLabel(challenged));
                JButton removeButton = new JButton("Verwijderen");
                removeButton.setActionCommand(challenged);
                removeButton.addActionListener(this);
                panel.getChallengedPanel().add(removeButton);
            }

            panel.getChallengedPanel().setLayout(new GridLayoutManager(2, this.challenged.size()));

            panel.revalidate();
            panel.repaint();
        }

        public void initialize() {
            panel.getChallengeButton().addActionListener(this);
            panel.getChallengeAllButton().addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("  challenge")) {
                String player = (String) panel.getPlayers().getSelectedItem();

                if(player != null) {
                    challenged.add(player);
                    update();
                }
            } else if(e.getActionCommand().equals("  challengeAll")) {
                controller.doChallengeAll(this.challenged);
            } else  {
                challenged.remove(e.getActionCommand());
                update();
            }
        }

        public void disable() {
            panel.getChallengeAllButton().setEnabled(false);
        }

        public void enable() {
            panel.getChallengeAllButton().setEnabled(true);
        }
    }

    public ChallengePanel(MainView.MainController controller) {
        this.controller = new ChallengeController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new VBoxLayoutManager());

        JLabel challengeLabel = new JLabel("Daag mensen uit:");

        JPanel challengePanel = new JPanel();
        challengePanel.setLayout(new HBoxLayoutManager());

        players = new JComboBox<String>();

        challengeButton = new JButton("Uitdagen");
        challengeButton.setActionCommand("  challenge");

        challengePanel.add(players);
        challengePanel.add(challengeButton);

        JLabel challengedPeople = new JLabel("Uitgedaagde mensen:");

        challengedPanel = new JPanel();

        challengeAllButton = new JButton("Deze mensen uitdagen");
        challengeAllButton.setActionCommand("  challengeAll");

        mainPanel.add(challengeLabel);
        mainPanel.add(challengePanel);
        mainPanel.add(Box.createGlue());
        mainPanel.add(challengedPeople);
        mainPanel.add(challengedPanel);
        mainPanel.add(challengeAllButton);

        serverPanel = new ServerPanel(controller);
        add(serverPanel);
        add(mainPanel);
        this.controller.initialize();
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ChallengePanel(null));
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
