package rolit.view.client;

import rolit.view.manager.VSplitLayoutManager;

import javax.swing.*;

public class ChatPanel extends JPanel {
    private JTextArea textArea;
    private JTextField textField;

    public ChatPanel() {
        setLayout(new VSplitLayoutManager(VSplitLayoutManager.VSplitType.Bottom));

        textArea = new JTextArea();
        textField = new JTextField();

        add(textArea);
        add(textField);
    }
}
