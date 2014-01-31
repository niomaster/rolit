package rolit.view.client;

import rolit.view.layout.VSplitLayoutManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatPanel extends JPanel {
    private final ChatController controller;
    private JTextArea textArea;
    private JTextField textField;

    public class ChatController implements KeyListener {
        private ChatPanel panel;
        private MainView.MainController controller;

        public ChatController(ChatPanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void initialize() {
            panel.getTextField().addKeyListener(this);
        }

        public void message(String user, String message) {
            panel.getTextArea().setText(user + ": " + message + "\n" + getTextArea().getText());
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                controller.doMessage(panel.getTextField().getText());
                panel.getTextField().setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        public void clear() {
            panel.getTextField().setText("");
        }
    }

    public ChatPanel(MainView.MainController controller) {
        this.controller = new ChatController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Bottom));

        textArea = new JTextArea();
        textField = new JTextField();

        add(textArea);
        add(textField);
        this.controller.initialize();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public ChatPanel.ChatController getController() {
        return controller;
    }
}
