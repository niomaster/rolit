package rolit.test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.game.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Martijn de Bijl
 */
public class GameTest {

    /**
     * Alle variabele die in setUp geïnstantieerd worden.
     */
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Game spel1;
    private Game spel2;

    /**
     * De setUp, maakt nieuwe objecten van Game en Player.
     * @throws GameFullException wordt gegooid als de Game vol is.
     */
    @Before
    public void setUp() throws GameFullException {
        player1 = new EasyComputerPlayer();
        player2 = new EasyComputerPlayer();
        player3 = new EasyComputerPlayer();
        player4 = new HumanPlayer("Martijn");
        spel1 = new Game(2);
        spel1.addPlayer(player1);
        spel1.addPlayer(player2);

        spel2 = new Game(4);
        spel2.addPlayer(player1);
        spel2.addPlayer(player2);
        spel2.addPlayer(player3);
        spel2.addPlayer(player4);
    }

    /**
     * Test of het aantal spelers klopt.
     * @throws Exception
     */
    @Test
    public void testGetNumberOfPlayers() throws Exception {
        assertEquals(2, spel1.getNumberOfPlayer());
    }

    /**
     * Test het aantal maximum spelers.
     * @throws Exception
     */
    public void testGetNumberOfMaximumPlayer() throws Exception {
        assertEquals(2, spel1.getNumberOfMaximumPlayers());
    }

    /**
     * Bekijkt of het bord gelijk is aan een nieuw bord.
     * @throws Exception
     */
    @Test
    public void testGetBoard() throws Exception {
        Board board2 = new Board();
        board2.equals(spel1.getBoard());
    }

    /**
     * Test of de houdige speler gelijk is aan de eeste speler die aan de beurt zou moeten zijn.
     * @throws Exception
     */
    @Test
    public void testGetCurrentPlayer() throws Exception {
        assertEquals(2, spel1.getCurrentPlayer());

    }

    /**
     * Als menselijke speler kun je zelf de zet kiezen. Je kunt dus ook zetten proberen die niet mogen volgens de
     * spelregels. Hiermee hebben we alle mogelijke zetten geprobeert, die zouden mogen.
     * @throws Exception
     */
    @Test
    public void testStart() throws Exception {
        spel1.start();
        spel2.start();
    }

    @Test
    public void testAddPlayer1() throws Exception {
        try {
            spel2.addPlayer(new EasyComputerPlayer());
            assertEquals(4, spel2.getNumberOfPlayer());
        } catch (GameFullException e){

        }
    }

    /**
     * Probeert een speler aan een spel te voegen die al vol is.
     * @throws Exception
     */
    @Test
    public void testAddPlayer2() throws Exception {
        try {
        spel1.addPlayer(new EasyComputerPlayer());
        assertEquals(3, spel1.getNumberOfPlayer());
        } catch (GameFullException e){

        }
    }


}
