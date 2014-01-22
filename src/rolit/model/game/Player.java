package rolit.model.game;

import rolit.util.Strings;

/**
 * De interface van een speler
 * @author Martijn de Bijl
 */
public interface Player {

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
    public Position determineMove(Board board);

    /**
     * Doet de zet die bepaald is in determineMove.
     * @param board het bord waarop de zet gedaan wordt.
     */
    public void doMove(Board board);

}
