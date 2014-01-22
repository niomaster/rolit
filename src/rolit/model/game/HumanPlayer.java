package rolit.model.game;

import rolit.util.Strings;

import java.util.Scanner;

/**
 * De klasse menselijke speler
 * Created by Martijn on 22-1-14.
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
     *
     * @param naam
     * @param nummer
     */
    public HumanPlayer(String naam, int nummer) {
        this.color = nummer;
        this.naam = naam;
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
        boolean valid = board.isLegalMove(this, readPosition(prompt));
        while (!valid) {
            System.out.println("ERROR: field " + choice + " is no valid choice.");
            choice = readPosition(prompt);
            valid = board.isLegalMove(this, readPosition(prompt));
        }
        return choice;
    }

    public void doMove(Board board) {
        Position positie = determineMove(board);
        board.doMove(this, positie);
    }

    /**
     * Leest de positie uit de console.
     * @param prompt de vraag die aan de speler gesteld word.
     * @return een positie die ingevuld is door de speler.
     */
    private Position readPosition(String prompt) {
        int value = 0;
        boolean intRead = false;

        do {
            System.out.print(prompt);
            String line = (new Scanner(System.in)).nextLine();
            Scanner scannerLine = new Scanner(line);
            if (scannerLine.hasNextInt()) {
                intRead = true;
                value = scannerLine.nextInt();
            }
        } while (!intRead);

        int x = value;

        value = 0;
        intRead = false;
        do {
            System.out.print(prompt);
            String line = (new Scanner(System.in)).nextLine();
            Scanner scannerLine = new Scanner(line);
            if (scannerLine.hasNextInt()) {
                intRead = true;
                value = scannerLine.nextInt();
            }
        } while (!intRead);

        int y = value;

        return new Position(x, y);
    }

    public static void main(String[] args){
        HumanPlayer player = new HumanPlayer("Rood", 0);
        Board bord = new Board();
        player.determineMove(bord);
    }
}
