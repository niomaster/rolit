package rolit.model.game;

import java.util.LinkedList;
import java.util.Random;

/**
 * De klasse makkelijke computer tegenstander.
 * @author Martijn de Bijl
 */
public class EasyComputerPlayer implements Player {
    /**
     * De kleur van de computer speler.
     */
    private int color;
    /**
     * De naam de de computer speler.
     */
    private final String naam = "Easy computer";

    /**
     * De constructor van de computer speler.
     */
    public EasyComputerPlayer() {

    }

    /**
     * Geeft een kleur aan een speler aan de hand van de hoeveelste speler hij is.
     * @param nummer de integer die de van de speler aangeeft.
     */
    public void setColor(int nummer) {
        this.color = nummer;
    }

    /**
     * Geeft de kleur terug van de computer speler.
     * @return een integer die de kleur van de speler representateerd.
     */
    public int getColor() {
        return color;
    }

    /**
     * Geeft de naam van de computer speler terug.
     * @return een string van de naam, altijd Easy computer.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Bepaald de volgende zet op een bepaald bord.
     * @param board het bord waarop de zet zal worden gedaan.
     * @return een positie op het bord.
     */
    public Position determineMove(Board board) {
        LinkedList<Position> possibleMoves = new LinkedList<Position>();
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                Position position = new Position(x, y);
                if (board.isLegalMove(this, position)) {
                    possibleMoves.add(position);
                }

            }
        }
        Random random = new Random();
        Position randomPosition = possibleMoves.get(random.nextInt(possibleMoves.size()));
        return randomPosition;
    }

    /**
     * Doet de zet bepaald met determineMove.
     * @param board het bord waarop de zet gedaan wordt.
     */
    public void doMove(Board board) {
        board.doMove(this, determineMove(board));
    }
}
