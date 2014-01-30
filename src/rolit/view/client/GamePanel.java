package rolit.view.client;

import rolit.model.game.Board;
import rolit.model.game.Position;
import rolit.model.networking.client.ClientGame;
import rolit.view.layout.GridLayoutManager;
import rolit.view.layout.HSplitLayoutManager;
import rolit.view.layout.VSplitLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class GamePanel extends JPanel {
    private final ServerPanel serverPanel;
    private final ChatPanel chatPanel;
    private final JPanel scorePanel;
    private final JButton[][] buttons;
    private GameController controller;

    public JPanel getScorePanel() {
        return scorePanel;
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public class GameController implements ActionListener {
        private GamePanel panel;
        private MainView.MainController controller;
        private boolean shouldMove = false;

        private final ImageIcon[] icons = {
                new ImageIcon((this.getClass().getResource("/resources/red.png"))),
                new ImageIcon((this.getClass().getResource("/resources/yellow.png"))),
                new ImageIcon((this.getClass().getResource("/resources/green.png"))),
                new ImageIcon((this.getClass().getResource("/resources/blue.png")))
        };

        public GameController(GamePanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void initialize() {
            for(int x = 0; x < Board.BOARD_WIDTH; x++) {
                for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
                    panel.getButtons()[x][y].addActionListener(this);
                }
            }
        }

        public void update() {
            panel.getServerPanel().getController().update();

            ClientGame game = controller.getGame(controller.getCurrentGame());

            panel.getScorePanel().removeAll();
            panel.getScorePanel().setLayout(null);

            for (int i = 0; i < game.getPlayers(); i++) {
                panel.getScorePanel().add(new JLabel(controller.getCurrentPlayers()[i]));
                panel.getScorePanel().add(new JLabel(controller.getCurrentGameBoard().getScore(i) + ""));
            }

            panel.getScorePanel().setLayout(new GridLayoutManager(2, game.getPlayers()));

            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
                    switch (controller.getCurrentGameBoard().getBoard().getField(x, y)) {
                        case 0:
                            panel.getButtons()[x][y].setIcon(icons[0]);
                            break;
                        case 1:
                            panel.getButtons()[x][y].setIcon(icons[1]);
                            break;
                        case 2:
                            panel.getButtons()[x][y].setIcon(icons[2]);
                            break;
                        case 3:
                            panel.getButtons()[x][y].setIcon(icons[3]);
                            break;
                        default:
                            panel.getButtons()[x][y].setIcon(null);
                    }
                }
            }
        }

        public void enableMove() {
            shouldMove = true;

            for(int x = 0; x < Board.BOARD_WIDTH; x++) {
                for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
                    panel.getButtons()[x][y].setContentAreaFilled(controller.getCurrentGameBoard().isLegalMove(controller.getCurrentGameBoard().getCurrentPlayer(), new Position(x, y)));
                }
            }
        }

        public void disableMove() {
            shouldMove = false;

            for(int x = 0; x < Board.BOARD_WIDTH; x++) {
                for(int y = 0; y < Board.BOARD_HEIGHT; y++) {
                    panel.getButtons()[x][y].setContentAreaFilled(false);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] location = e.getActionCommand().split(";");
            int x = Integer.parseInt(location[0]);
            int y = Integer.parseInt(location[1]);

            if(shouldMove && controller.getCurrentGameBoard().isLegalMove(controller.getCurrentGameBoard().getCurrentPlayer(), new Position(x, y))) {
                controller.doMove(x, y);
            }
        }
    }

    public GamePanel(MainView.MainController controller) {
        this.controller = new GameController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Top));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new HSplitLayoutManager(200, HSplitLayoutManager.HSplitType.Right));

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(8, 8));

        buttons = new JButton[8][8];

        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                buttons[x][y] = new JButton("");
                buttons[x][y].setContentAreaFilled(false);
                buttons[x][y].setActionCommand(x + ";" + y);
                gamePanel.add(buttons[x][y]);
            }
        }

        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new VSplitLayoutManager(100, VSplitLayoutManager.VSplitType.Top));

        scorePanel = new JPanel();

        sideBarPanel.add(scorePanel);
        chatPanel = new ChatPanel(controller);
        sideBarPanel.add(chatPanel);

        mainPanel.add(gamePanel);
        mainPanel.add(sideBarPanel);

        serverPanel = new ServerPanel(controller);
        add(serverPanel);
        add(mainPanel);
        this.controller.initialize();
    }

    public ServerPanel getServerPanel() {
        return serverPanel;
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public GameController getController() {
        return controller;
    }
}
