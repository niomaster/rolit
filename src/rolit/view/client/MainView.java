package rolit.view.client;

import rolit.model.game.Game;
import rolit.model.game.Position;
import rolit.model.networking.client.ClientGame;
import rolit.model.networking.client.ServerHandler;
import rolit.model.networking.common.Packet;
import rolit.model.networking.server.*;
import rolit.util.Arrays;
import rolit.view.client.action.*;
import rolit.view.client.action.Action;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class MainView extends JFrame {
    private final MainController controller;

    /**
     * Zorgt er voor dat de view de goede panel laat zien en dat de juiste acties op de achtergrond gebeuren.
     */
    public class MainController {
        private final MainView view;

        private final ConnectPanel connectPanel;
        private final GameListPanel gameListPanel;
        private final WaitPanel waitPanel;
        private final GamePanel gamePanel;
        private final ChallengePanel challengePanel;

        private ServerHandler currentServer = null;
        private String currentGame = null;
        private String currentUser = null;
        private String[] currentPlayers;
        private Game currentGameBoard;

        private HashMap<String, ClientGame> games = new HashMap<String, ClientGame>();
        private LinkedList<String> canBeChallenged = new LinkedList<String>();

        public void gameUpdate(GamePacket packet) {
            if(games.get(packet.getGame()) == null) {
                games.put(packet.getGame(), new ClientGame(packet.getGame(), packet.getPlayers(), packet.getStatus()));
            } else {
                games.get(packet.getGame()).setPlayers(packet.getPlayers());
                games.get(packet.getGame()).setStatus(packet.getStatus());
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gameListPanel.getController().updateGames(games.values());
                    waitPanel.getController().updateGames(games.values());
                    view.revalidate();
                }
            });
        }

        public void message(MessagePacket packet) {
            gameListPanel.getChatPanel().getController().message(packet.getUser(), packet.getMessage());
            waitPanel.getChatPanel().getController().message(packet.getUser(), packet.getMessage());
            gamePanel.getChatPanel().getController().message(packet.getUser(), packet.getMessage());
        }

        public void challenge(ChallengePacket packet) {
            // TODO implement
        }

        public void online(OnlinePacket packet) {
            // TODO add UI and implement
        }

        public void canBeChallenged(CanBeChallengedPacket packet) {
            if(packet.isCanBeChallenged()) {
                if(!canBeChallenged.contains(packet.getUser())) {
                    canBeChallenged.add(packet.getUser());
                }
            } else {
                canBeChallenged.remove(packet.getUser());
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    challengePanel.getController().updateCanBeChallenged();
                }
            });
        }

        public ServerHandler getCurrentServer() {
            return currentServer;
        }

        public String getCurrentGame() {
            return currentGame;
        }

        public String getCurrentUser() {
            return currentUser;
        }

        public void doCreateGame() {
            gameListPanel.getController().disable();
            doAction(new CreateGameAction(this, currentServer));
        }

        public void doJoinGame(String creator) {
            gameListPanel.getController().disable();
            doAction(new JoinGameAction(this, currentServer, creator));
        }

        public ClientGame getGame(String currentGame) {
            return games.get(currentGame);
        }

        public void doStart() {
            doAction(new StartAction(this, currentServer));
        }

        public String[] getCurrentPlayers() {
            return currentPlayers;
        }

        public Game getCurrentGameBoard() {
            return currentGameBoard;
        }

        public void doMove(int x, int y) {
            gamePanel.getController().disableMove();
            doAction(new MoveAction(this, currentServer, x, y));
        }

        public void doDisconnect() {
            ServerHandler server = currentServer;

            currentPlayers = null;
            currentGame = null;
            currentGameBoard = null;
            currentUser = null;
            currentServer = null;

            doAction(new DisconnectAction(this, server));
        }

        public void doHighscoreDate() {
            String date = JOptionPane.showInputDialog(null, "Vul een datum in (yyyy-MM-dd)");

            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(date);
                doHighscore("date", date);
            } catch (ParseException e) {
                info("Geen geldige datum");
            }
        }

        private void doHighscore(String type, String arg) {
            doAction(new HighscoreAction(this, currentServer, type, arg));
        }

        public void doHighscorePlayer() {
            String player = JOptionPane.showInputDialog(null, "Vul een speler in");
            doHighscore("player", player);
        }

        public LinkedList<String> getCanBeChallenged() {
            return canBeChallenged;
        }

        public void doChallenge() {
            challengePanel.getController().update();
            switchTo(challengePanel);
        }


        public void doChallengeAll(LinkedList<String> challenged) {
            challengePanel.getController().disable();
            doAction(new ChallengeAction(this, currentServer, Arrays.cast(String.class, challenged.toArray())));
        }

        private class SwitchRunnable implements Runnable {
            private MainView view;
            private JPanel switchTo;

            public SwitchRunnable(MainView view, JPanel switchTo) {
                this.view = view;
                this.switchTo = switchTo;
            }

            @Override
            public void run() {
                view.getContentPane().remove(0);
                view.getContentPane().add(switchTo);
                view.getContentPane().revalidate();
                view.getContentPane().repaint();
            }
        }

        public Collection<ClientGame> getGames() {
            return games.values();
        }

        public MainController(MainView view) {
            this.view = view;
            this.connectPanel = new ConnectPanel(this);
            this.gameListPanel = new GameListPanel(this);
            this.waitPanel = new WaitPanel(this);
            this.gamePanel = new GamePanel(this);
            this.challengePanel = new ChallengePanel(this);
        }

        public void initialize() {
            getContentPane().add(connectPanel);
            view.setVisible(true);
        }

        /**
         * such abstraction
         * much enterprise
         * wow
         * @param action de actie die op de achtergrond moet worden uitgevoerd.
         */
        private void doAction(Action action) {
            action.start();
        }

        public void doConnect(String hostnamePort, String userName, String password) {
            connectPanel.getController().disable();
            doAction(new ConnectAction(hostnamePort, userName, password, this));
        }

        public synchronized void actionSucceeded(Action action) {
            if(action instanceof ConnectAction) {
                ConnectAction connectAction = (ConnectAction) action;
                currentServer = connectAction.getServerHandler();
                currentUser = connectAction.getUserName();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gameListPanel.getServerPanel().getController().update();
                        switchTo(gameListPanel);
                        connectPanel.getController().enable();
                    }
                });

                new EventThread(this, connectAction.getEventStream()).start();
            } else if(action instanceof CreateGameAction) {
                currentGame = getCurrentUser();


                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        waitPanel.getController().update();
                        switchTo(waitPanel);
                        gameListPanel.getController().enable();
                    }
                });

                doWaitForStart();
            } else if(action instanceof JoinGameAction) {
                currentGame = ((JoinGameAction) action).getCreator();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        waitPanel.getController().update();
                        switchTo(waitPanel);
                        gameListPanel.getController().enable();
                    }
                });

                doWaitForStart();
            } else if(action instanceof WaitForStartAction) {
                currentPlayers = ((WaitForStartAction) action).getPlayers();
                currentGameBoard = new Game(currentPlayers.length);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.getController().update();
                        switchTo(gamePanel);
                        waitPanel.getController().enable();
                    }
                });

                doAction(new WaitForGameEventAction(this, currentServer));
            } else if(action instanceof WaitForGameEventAction) {
                Packet event = ((WaitForGameEventAction) action).getEventPacket();

                if(event instanceof MovePacket) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            gamePanel.getController().enableMove();
                        }
                    });
                } else if(event instanceof MoveDonePacket) {
                    MoveDonePacket move = (MoveDonePacket) event;
                    currentGameBoard.doMove(currentGameBoard.getCurrentPlayer(), new Position(move.getX(), move.getY()));
                    currentGameBoard.nextPlayer();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            gamePanel.getController().update();
                        }
                    });
                } else if(event instanceof GameOverPacket) {
                    GameOverPacket packet = (GameOverPacket) event;

                    if(packet.getWinners().length == 1) {
                        info("Het spel is afgelopen! De winnaar is " + packet.getWinners()[0] + " met " + packet.getScore() + " punten");
                    } else {
                        String winner = "";

                        for(int i = 0; i < packet.getWinners().length - 1; i++) {
                            if(i != 0) {
                                winner += ", ";
                            }

                            winner += packet.getWinners()[i];
                        }

                        winner += " en " + packet.getWinners()[packet.getWinners().length - 1];

                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                currentGameBoard = null;
                                currentGame = null;
                                currentPlayers = null;
                                switchTo(gameListPanel);

                            }
                        });

                        info("Het spel is afgelopen! De winnaars zijn " + winner + " met " + packet.getScore() + " punten");
                    }
                }

                doAction(new WaitForGameEventAction(this, currentServer));
            } else if(action instanceof DisconnectAction) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        currentServer = null;
                        currentPlayers = null;
                        currentGame = null;
                        currentGameBoard = null;
                        currentUser = null;
                        switchTo(connectPanel);
                    }
                });
            } else if(action instanceof HighscoreAction) {
                info("De highscore daarvan is " + ((HighscoreAction) action).getResult().getDataField()[0]);
            } else if(action instanceof ChallengeAction) {
                games.put(getCurrentUser(), new ClientGame(getCurrentUser(), 1, ServerProtocol.STATUS_NOT_STARTED));
                currentGame = getCurrentUser();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        waitPanel.getController().updateGames(getGames());
                        switchTo(waitPanel);
                        challengePanel.getController().enable();
                    }
                });

                doWaitForStart();
            }
        }

        private void doWaitForStart() {
            doAction(new WaitForStartAction(this, currentServer, currentGame));
        }

        private void switchTo(JPanel panel) {
            SwingUtilities.invokeLater(new SwitchRunnable(view, panel));
        }

        public synchronized void actionFailed(Action action) {
            if(action instanceof ConnectAction) {
                ConnectAction connectAction = (ConnectAction) action;

                if(connectAction.getError() != null) {
                    info("Kon niet verbinden met " + connectAction.getHostnamePort() + ": " + connectAction.getError());
                } else {
                    info("Kon niet verbinden met " + connectAction.getHostnamePort());
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        connectPanel.getController().enable();
                    }
                });
            } else if(action instanceof CreateGameAction) {
                info("Kon geen spel maken");

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gameListPanel.getController().enable();
                    }
                });
            } else if(action instanceof JoinGameAction) {
                info("Je kunt niet meedoen met dit spel");

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gameListPanel.getController().enable();
                    }
                });
            } else if(action instanceof WaitForStartAction) {
                if(currentServer != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            info("Het spel werd voortijdig afgebroken door de server");
                            gameListPanel.getController().updateGames(games.values());
                            switchTo(gameListPanel);
                            waitPanel.getController().enable();
                        }
                    });
                }
            } else if(action instanceof WaitForGameEventAction) {
                if(currentServer != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            info("Het spel werd afgebroken door de server");
                            switchTo(connectPanel);
                        }
                    });
                }
            } else if(action instanceof DisconnectAction) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        switchTo(connectPanel);
                    }
                });
            }
        }
    }

    public MainView() {
        this.controller = new MainController(this);

        setSize(1024, 768);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controller.initialize();
    }

    public static void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}