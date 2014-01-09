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

    public static final String CREATE_GAME = "createGame";

    public static final String JOIN_GAME = "joinGame";

    public static final String START_GAME = "startGame";

    public static final String MOVE = "move";

    public static final String MESSAGE = "message";

    public static final String CHALLENGE = "challenge";

    public static final String CHALLENGE_RESPONSE = "challengeResponse";


}
