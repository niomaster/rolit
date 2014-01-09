package rolit.model.networking;

/**
 * @author Pieter Bos
 * @author Martijn de Bijl
 *
 * Abstract class met alle constanten en methodes die gebruikt kunnen worden
 */
public abstract class ServerProtocol {
    /**
     * Constante voor het handshake-commando
     */
    public static final String HANDSHAKE = "hello";

    /**
     * Constante voor het error-commando
     */
    public static final String ERROR = "error";

    /**
     * Constante voor het game-commando
     */
    public static final String GAME = "game";

    /**
     * Constante voor het start-commando
     */
    public static final String START = "start";

    /**
     * Constante voor het move-commando
     */
    public static final String MOVE = "move";

    /**
     * Constante voor het move-done-commando
     */
    public static final String MOVE_DONE = "moveDone";

    /**
     * Constante voor het game-over-command
     */
    public static final String GAME_OVER = "gameOver";

    /**
     * Constante voor het message-commando
     */
    public static final String MESSAGE = "message";

    /**
     * Constante voor het challenge-commando
     */
    public static final String CHALLENGE = "challenge";

    /**
     * Constante voor het challenge-response-commando
     */
    public static final String CHALLENGE_RESPONSE = "challengeResponse";

    /**
     * Constante voor het can-be-challenged-commando
     */
    public static final String CAN_BE_CHALLENGED = "canBeChallenged";

    /**
     * Constante voor highscore
     */
    public static final String HIGHSCORE = "highscore";

    public static final int ERROR_GENERIC = -1;
    public static final int ERROR_INVALID_NAME = 1;
    public static final int ERROR_GAME_FULL = 2;
    public static final int ERROR_TOO_LITTLE_PLAYERS = 3;
    public static final int ERROR_INVALID_MOVE = 4;
    public static final int ERROR_NO_SUCH_GAME = 5;
    public static final int ERROR_USER_HAS_NO_GAME = 6;
    public static final int ERROR_HANDSHAKE_MISSING = 7;
    public static final int ERROR_USER_ALREADY_HAS_GAME = 8;

    /**
     * Antwoord op de handshake van de client. Moet altijd het eerst verzonden commando zijn, met uitzondering van
     * errors.
     * @param version De protocolversie van de server of wat de server ondersteunt.
     */
    public abstract void handshake(int version);

    /**
     * Commando om de client te laten weten dat hij iets fout heeft gedaan, waardoor de verbinding moet worden
     * verbroken.
     * @param errorCode De error-code, op te zoeken in de errorCode-tabel.
     */
    public abstract void error(int errorCode);

    /**
     * Commando om de client te laten weten dat er óf een nieuw spel is, óf dat er een spel is veranderd in status.
     * Clients krijgen een serie van deze commando's na de handshake om zo een lijst van alle spellen op te bouwen. Als
     * er daarna iets verandert aan het aantal spelers of dat het spel is begonnen moet de server weer een update
     * sturen.
     * @param creator De maker van het spel.
     * @param hasStarted Of het spel al begonnen is.
     * @param noPlayers Het aantal spelers in het spel.
     */
    public abstract void game(String creator, boolean hasStarted, int noPlayers);

    /**
     * Commando om een spel te starten met twee spelers, die in die volgorde een zet moeten doen.
     * @param playerOne De eerste speler
     * @param playerTwo De tweede speler
     */
    public abstract void start(String playerOne, String playerTwo);

    /**
     * Commando om een spel te starten met drie spelers, die in die volgorde een zet moeten doen.
     * @param playerOne De eerste speler
     * @param playerTwo De tweede speler
     * @param playerThree De derde speler
     */
    public abstract void start(String playerOne, String playerTwo, String playerThree);

    /**
     * Commando om een spel te starten met vier spelers, die in die volgorde een zet moeten doen.
     * @param playerOne De eerste speler
     * @param playerTwo De tweede speler
     * @param playerThree De derde speler
     * @param playerFour De vierde speler
     */
    public abstract void start(String playerOne, String playerTwo, String playerThree, String playerFour);

    /**
     * Commando om de client te vertellen dat hij een zet moet gaan doen.
     */
    public abstract void move();

    /**
     * Commando om de client te laten weten dat iemand een zet heeft gedaan in het huidige spel.
     * @param name Naam van de speler die de zet heeft gedan.
     * @param x X-coördinaat, waarbij de linkerkant 0 is en de rechterkant 7.
     * @param y Y-coördinaat, waarbij de bovenkant 0 is en de onderkant 7.
     */
    public abstract void moveDone(String name, int x, int y);

    /**
     * Commando om de client te laten weten dat het spel is afgelopen, om welke reden dan ook. Eventueel zijn er
     * winnaars als het spel helemaal is voltooid. De server mag bepalen wat er gebeurt als er meerdere mensen dezelfde
     * score hebben.
     * @param score De hoogste score
     * @param winners De mensen met die score
     */
    public abstract void gameOver(int score, String[] winners);

    /**
     * Commando om de client op te hoogte te stellen van een chatbericht
     * @param name Afzender van het chatbericht
     * @param body Tekst van het chatbericht
     */
    public abstract void message(String name, String body);

    /**
     * Commando om de client op te hoogte te stellen van een uitdaging met twee mensen
     * @param challenger De uitdager
     */
    public abstract void challenge(String challenger);

    /**
     * Commando om de client op de hoogte te stellen van een uitdaging met drie mensen
     * @param challenger De uitdager
     * @param other1 Andere gebruiker
     */
    public abstract void challenge(String challenger, String other1);

    /**
     * Commando om de client op de hoogte te stellen van een uitdaging met vier mensen
     * @param challenger De uitdager
     * @param other1 Andere gebruiker 1
     * @param other2 Andere gebruiker 2
     */
    public abstract void challenge(String challenger, String other1, String other2);

    /**
     * Commando om mensen van een uitdaging op de hoogte te stellen van de status van de andere uitgedaagden.
     * @param name Naam van de uitgedaagde.
     * @param accept Of deze persoon accepteert.
     */
    public abstract void challengeResponse(String name, boolean accept);

    /**
     * Commando om de client op de hoogte te stellen van het veranderen van de status van iemand.
     * @param name Naam van de uitgedaagde
     * @param flag Of hij kan worden uitgedaagd.
     */
    public abstract void canBeChallenged(String name, boolean flag);

    /**
     * Commando om de client op de hoogte te stellen van de gevraagde highscores.
     * @param args Argumenten
     */
    public abstract void highscore(String[] args);
}
