package rolit.model.game.Test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.game.*;

import static junit.framework.Assert.assertEquals;

/**
 * Test game.
 * @author Martijn de Bijl
 */
public class GameTest {

    private Game game2;
    private Game game3;
    private Game game4;


    @Before
    public void setUp() throws Exception {
        game2 = new Game(2);
        game3 = new Game(3);
        game4 = new Game(4);

        game2.addPlayer(new EasyComputerPlayer());
        game2.addPlayer(new HumanPlayer("Martijn"));

        game3.addPlayer(new EasyComputerPlayer());
        game3.addPlayer(new EasyComputerPlayer());
        game3.addPlayer(new EasyComputerPlayer());


        game4.addPlayer(new EasyComputerPlayer());
        game4.addPlayer(new EasyComputerPlayer());
        game4.addPlayer(new EasyComputerPlayer());
        game4.addPlayer(new EasyComputerPlayer());
    }

    @Test
    public void testGetBoard() throws Exception {
        Board board = new Board();
        game2.getBoard().equals(board);
    }

    @Test
    public void testGetCurrentPlayer() throws Exception {
        assertEquals(2,game2.getCurrentPlayer());
    }

    @Test
    public void testStart2() throws Exception {
        game2.start();
    }

    @Test
    public void testStart3() throws Exception {
        game3.start();
    }

    @Test
    public void testStart4() throws Exception {
        game4.start();
    }

    @Test
    public void testAddPlayer() throws Exception {
        game2.addPlayer(new EasyComputerPlayer());
    }


}
