package rolit.model.game;

import rolit.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * De klasse menselijke speler
 * @author Martijn de Bijl
 */
public class HumanPlayer implements Player{
    /**
     * De kleur van de speler
     */
    private int color;
    /**
     * De naam van de speler
     */
    private String naam;

    /**
     * Maakt een nieuwe menselijke speler met een naam.
     * @param naam de naam van de speler.
     */
    public HumanPlayer(String naam) {
        this.naam = naam;
    }

    /**
     * Geeft een kleur aan een speler aan de hand van de hoeveelste speler hij is.
     * @param nummer de integer die de van de speler aangeeft.
     */
    public void setColor(int nummer) {
        this.color = nummer;
    }

    /**
     * Geeft de kleur van de speler terug.
     * @return een integer die de kleur representeerd.
     */
    public int getColor() {
        return color;
    }

    /**
     * Geeft de naam van de speler terug.
     * @return een string met de naam van de speler.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Vraagt via de console om een x en een y coördinaat in te vullen.
     * @param board het bord waar de zet op gedaan zal worden.
     * @return een positie op het bord.
     */
    public Position determineMove(Board board) {
        String prompt = "> " + getNaam() + " (" + getColor() + ")" + ", what is your choice? ";
        Position choice = readPosition(prompt);
        boolean valid = board.isLegalMove(this.getColor(), choice);
        while (!valid) {
            System.out.println("ERROR: field " + choice + " is no valid choice.");
            choice = readPosition(prompt);
            valid = board.isLegalMove(this.getColor(), choice);
        }
        return choice;
    }

    public void doMove(Board board) {
        Position positie = determineMove(board);
        board.doMove(this.getColor(), positie);
    }

    /**
     * Leest de positie uit de console.
     * @param prompt de vraag die aan de speler gesteld word.
     * @return een positie die ingevuld is door de speler.
     */
    private Position readPosition(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        String line = null;

        try {
            line = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            line = null;
        }

        return new Position(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]));
    }
}
