package rolit.view.client;

import rolit.view.layout.VSplitLayoutManager;

import javax.swing.*;

public class ChatPanel extends JPanel {
    private final ChatController controller;
    private JTextArea textArea;
    private JTextField textField;

    public class ChatController {
        private ChatPanel panel;
        private MainView.MainController controller;

        public ChatController(ChatPanel panel, MainView.MainController controller) {
            this.panel = panel;
            this.controller = controller;
        }

        public void initialize() {

        }

        public void message(String user, String message) {
            panel.getTextArea().setText(getTextArea().getText() + "\n" + user + ": " + message);
        }
    }

    public ChatPanel(MainView.MainController controller) {
        this.controller = new ChatController(this, controller);
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Bottom));

        textArea = new JTextArea();
        textField = new JTextField();

        add(textArea);
        add(textField);
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
