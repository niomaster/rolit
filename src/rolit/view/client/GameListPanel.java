package rolit.view.client;

import javafx.fxml.FXMLLoader;
import rolit.model.networking.client.ClientGame;
import rolit.model.networking.server.ServerProtocol;
import rolit.view.layout.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class GameListPanel extends JPanel {
    private final JPanel listPanel;
    private final ChatPanel chatPanel;
    private final JButton createGameButton;
    private final JButton challengeButton;
    private final JButton highscorePlayerButton;
    private final JButton highscoreDateButton;
    private GameListController controller;
    private ServerPanel serverPanel;

    public JButton getCreateGameButton() {
        return createGameButton;
    }

    public JButton getChallengeButton() {
        return challengeButton;
    }

    public ServerPanel getServerPanel() {
        return serverPanel;
    }

    public JButton getHighscorePlayerButton() {
        return highscorePlayerButton;
    }

    public JButton getHighscoreDateButton() {
        return highscoreDateButton;
    }

    public class GameListController implements ActionListener {
        private GameListPanel panel;
        private MainView.MainController controller;

        public GameListController(GameListPanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void initialize() {
            panel.getChallengeButton().addActionListener(this);
            panel.getCreateGameButton().addActionListener(this);
            panel.getHighscorePlayerButton().addActionListener(this);
            panel.getHighscoreDateButton().addActionListener(this);
        }

        public void updateGames(Collection<ClientGame> values) {
            JPanel panel = this.panel.getListPanel();
            panel.removeAll();
            panel.setLayout(null);

            panel.add(new JLabel("Spelmaker"));
            panel.add(new JLabel("Aantal spelers"));
            panel.add(Box.createGlue());

            for(ClientGame game : values) {
                if(game.getStatus() != ServerProtocol.STATUS_NOT_STARTED) {
                    continue;
                }

                panel.add(new JLabel(game.getCreator()));
                panel.add(new JLabel(game.getPlayers() + ""));

                JButton joinButton = new JButton("Meedoen");
                joinButton.setActionCommand(game.getCreator());

                if(game.getPlayers() >= 4) {
                    joinButton.setEnabled(false);
                }

                joinButton.addActionListener(this);

                panel.add(joinButton);
            }

            panel.setLayout(new GridLayoutManager(3, values.size() + 1, 8));
            panel.revalidate();
            panel.repaint();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("  createGame")) {
                controller.doCreateGame();
            } else if(e.getActionCommand().equals("  challenge")) {
                controller.doChallenge();
            } else if(e.getActionCommand().equals("  highscoreDate")) {
                controller.doHighscoreDate();
            } else if(e.getActionCommand().equals("  highscorePlayer")) {
                controller.doHighscorePlayer();
            } else {
                controller.doJoinGame(e.getActionCommand());
            }
        }

        public void enable() {
            panel.getServerPanel().getController().enable();
            panel.getCreateGameButton().setEnabled(true);
            panel.getChallengeButton().setEnabled(true);
            this.updateGames(controller.getGames());
        }

        public void disable() {
            panel.getServerPanel().getController().disable();
            panel.getCreateGameButton().setEnabled(false);
            panel.getChallengeButton().setEnabled(false);

            for(Component component : panel.getListPanel().getComponents()) {
                if(component instanceof JButton) {
                    ((JButton) component).setEnabled(false);
                }
            }
        }
    }

    public GameListPanel(MainView.MainController controller) {
        this.controller = new GameListController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new HSplitLayoutManager(200, HSplitLayoutManager.HSplitType.Right));

        JPanel gamesPanel = new JPanel();
        gamesPanel.setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Bottom));

        listPanel = new JPanel();
        listPanel.setLayout(new VBoxLayoutManager());

        listPanel.add(new JLabel("Nog geen spellen..."));

        JPanel buttonsArrayPanel = new JPanel();
        buttonsArrayPanel.setLayout(new HBoxLayoutManager());

        createGameButton = new JButton("Maak spel");
        createGameButton.setActionCommand("  createGame");
        challengeButton = new JButton("Uitdagen");
        challengeButton.setActionCommand("  challenge");
        JLabel highscoreLabel = new JLabel("Highscores:");
        highscorePlayerButton = new JButton("Speler");
        highscorePlayerButton.setActionCommand("  highscorePlayer");
        highscoreDateButton = new JButton("Dag");
        highscoreDateButton.setActionCommand("  highscoreDate");

        buttonsArrayPanel.add(createGameButton);
        buttonsArrayPanel.add(challengeButton);
        buttonsArrayPanel.add(highscoreLabel);
        buttonsArrayPanel.add(highscorePlayerButton);
        buttonsArrayPanel.add(highscoreDateButton);

        gamesPanel.add(listPanel);
        gamesPanel.add(buttonsArrayPanel);

        mainPanel.add(gamesPanel);
        chatPanel = new ChatPanel(controller);
        mainPanel.add(chatPanel);

        serverPanel = new ServerPanel(controller);
        add(serverPanel);
        add(mainPanel);

        this.controller.initialize();
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public JPanel getListPanel() {
        return listPanel;
    }

    public GameListController getController() {
        return controller;
    }
}
