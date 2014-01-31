package rolit.view.client;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * De interface voor het leaderboard.
 * @author Martijn de Bijl
 */
public class LeaderBoardPanel extends JPanel {

    public LeaderBoardPanel(){
        JLabel selectDay = new JLabel("Voer een dag in");
        JTextField dayInput = new JTextField("");

        JLabel selectPlayer = new JLabel("Voer een speler in");
        JTextField playerInput = new JTextField("");

        JTextArea leaderBoard = new JTextArea("");

        add(selectDay);
        add(dayInput);
        add(selectPlayer);
        add(playerInput);
        add(leaderBoard);
    }



}
