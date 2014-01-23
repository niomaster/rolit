package rolit.model.game;

import rolit.util.Strings;

import java.io.IOException;

/**
 * De interface van een speler
 * @author Martijn de Bijl
 */
public interface Player {

    /**
     * Geeft een kleur aan een speler aan de hand van de hoeveelste speler hij is.
     * @param nummer de integer die de van de speler aangeeft.
     */
    public void setColor(int nummer);

    /**
     * Geeft de kleur van de speler terug.
     * @return een integer die de kleur representeerd.
     */
    public int getColor();

    /**
     * Geeft de naam van de speler terug.
     * @return een string met de naam van de speler.
     */
    public String getNaam();

    /**
     * Bepaald wat de volgende zet zal worden.
     * @param board het bord waarop de zet zal worden gedaan.
     * @return een positie op het bord, dat een legale zet is.
     */
    public Position determineMove(Board board) throws IOException;

    /**
     * Doet de zet die bepaald is in determineMove.
     * @param board het bord waarop de zet gedaan wordt.
     */
    public void doMove(Board board) throws IOException;

}
