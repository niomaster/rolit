package rolit.view.client;

import rolit.model.networking.client.ClientGame;
import rolit.view.layout.HSplitLayoutManager;
import rolit.view.layout.VSplitLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class WaitPanel extends JPanel {
    private final JLabel status;
    private final JButton actionButton;
    private final WaitController controller;
    private final JLabel creator;
    private final ServerPanel serverPanel;
    private ChatPanel chatPanel;

    public WaitController getController() {
        return controller;
    }

    public JLabel getCreator() {
        return creator;
    }

    public ServerPanel getServerPanel() {
        return serverPanel;
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public class WaitController implements ActionListener {
        private WaitPanel panel;
        private MainView.MainController controller;
        private ClientGame game;

        public WaitController(WaitPanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void updateGames(Collection<ClientGame> games) {
            if(controller.getCurrentGame() != null) {
                game = controller.getGame(controller.getCurrentGame());

                creator.setText("Het spel van " + game.getCreator() + " is nog niet begonnen.");
                status.setText("Er zitten nu " + game.getPlayers() + " mensen in het spel.");
            }

            update();
        }

        public void initialize() {
            panel.getActionButton().addActionListener(this);
        }

        public void update() {
            panel.getServerPanel().getController().update();
            panel.getActionButton().setEnabled(game != null && controller.getCurrentUser().equals(game.getCreator()) && game.getPlayers() >= 2);
        }

        public void enable() {
            panel.getServerPanel().getController().enable();
        }

        public void disable() {
            panel.getServerPanel().getController().disable();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.doStart();
        }
    }

    public WaitPanel(MainView.MainController controller) {
        this.controller = new WaitController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new HSplitLayoutManager(200, HSplitLayoutManager.HSplitType.Right));

        JPanel startPanel = new JPanel();

        creator = new JLabel("Het spel van  is nog niet begonnen");
        status = new JLabel("Er zitten nu  mensen in het spel.");
        actionButton = new JButton("Beginnen");
        actionButton.setEnabled(false);

        startPanel.add(creator);
        startPanel.add(status);
        startPanel.add(actionButton);

        mainPanel.add(startPanel);
        chatPanel = new ChatPanel(controller);
        mainPanel.add(chatPanel);

        serverPanel = new ServerPanel(controller);
        add(serverPanel);
        add(mainPanel);
        this.controller.initialize();
    }

    public JLabel getStatus() {
        return status;
    }

    public JButton getActionButton() {
        return actionButton;
    }
}
