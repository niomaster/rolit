package rolit.model.networking;

/**
 * @author Pieter Bos
 * @author Martijn de Bijl
 *
 * Abstract class met alle constanten en methodes die gebruikt kunnen worden
 */
public abstract class ClientProtocol {
    /**
     * Constante voor het handshake-commando
     */
    public static final String HANDSHAKE = "hello";

    /**
     * Constante voor het create-gamecommando
     */
    public static final String CREATE_GAME = "createGame";

    /**
     * Constante voor het join-gamecommando
     */
    public static final String JOIN_GAME = "joinGame";

    /**
     * Constante voor het start-gamecommando
     */
    public static final String START_GAME = "startGame";

    /**
     * Constante voor het movecommando
     */
    public static final String MOVE = "move";

    /**
     * Constante voor het messagecommando
     */
    public static final String MESSAGE = "message";

    /**
     * Constante voor het challengecommando
     */
    public static final String CHALLENGE = "challenge";

    /**
     * Constante voor het challenge-responsecommando
     */
    public static final String CHALLENGE_RESPONSE = "challengeResponse";

    /**
     * Constante voor het highscorecommando
     */
    public static final String HIGHSCORE = "highscore";

    /**
     * Handshake voor de server. Moet altijd het eerste verzonden pakket zijn, met uitzondering van de errors.
     * @param clientName Naam van de client.
     * @param version Versie van het clientprotocol
     */
    public abstract void hello(String clientName, int version);

    /**
     * Maak een spel
     */
    public abstract void createGame();

    /**
     * Join een spel
     * @param creator De maker van het spel
     */
    public abstract void joinGame(String creator);

    /**
     * Start het spel waarvan de gebruiker de creator is.
     */
    public abstract void startGame();

    /**
     * Doe een zet
     * @param x X-coördinaat
     * @param y Y-coördinaat
     */
    public abstract void move(int x, int y);

    /**
     * Stuur een bericht naar iedereen in de lobby of iedereen in het spel
     * @param body Het bericht
     */
    public abstract void message(String body);

    /**
     * Daag één ander uit voor een spel
     * @param other De ander
     */
    public abstract void challenge(String other);

    /**
     * Daag twee anderen uit voor een spel
     * @param other1 De een
     * @param other2 De ander
     */
    public abstract void challenge(String other1, String other2);

    /**
     * Daag drie anderen uit voor een spel
     * @param other1 De eerste andere
     * @param other2 De tweede andere
     * @param other3 De derder andere
     */
    public abstract void challenge(String other1, String other2, String other3);

    /**
     * Reageer op een uitdaging
     * @param accept Of de client accepteert
     */
    public abstract void challengeResponse(boolean accept);

    /**
     * Vraag highscores op bij de server
     * @param type Type highscore (bijv. date, player)
     * @param arg Een argument (bijv. 2014-01-01)
     */
    public abstract void highscore(String type, String arg);
}
