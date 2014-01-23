package rolit.model.game.Test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.game.Board;
import rolit.model.game.HumanPlayer;
import rolit.model.game.Player;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Martijn on 23-1-14.
 */
public class HumanPlayerTest {

    private Player player;
    private Board board;

    @Before
    public void setUp(){
        board = new Board();
        player = new HumanPlayer("Martijn");
    }

    @Test
    public void testSetColor() throws Exception {
        player.setColor(0);
        assertEquals(0, player.getColor());
    }

    @Test
    public void testGetColor() throws Exception {
        player.setColor(2);
        assertEquals(2,player.getColor());
    }

    @Test
    public void testGetNaam() throws Exception {
        assertEquals("Martijn", player.getNaam());
    }

    @Test
    public void testDetermineMove() throws Exception {
        player.determineMove(board);
    }

    @Test
    public void testDoMove() throws Exception {
        player.doMove(board);
    }
}
