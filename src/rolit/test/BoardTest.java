package rolit.test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.game.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Martijn de Bijl
 */
public class BoardTest {

    private Board board;
    private Player speler;
    Position positie;

    @Before
    public void setUp(){
        speler = new EasyComputerPlayer();
        board = new Board();
        positie = new Position(5,5);
        speler.setColor(0);
    }

    /**
     * Test of het bord de goede staat van een veld teruggeeft.
     * @throws Exception
     */
    @Test
    public void testGetField() throws Exception {
        assertEquals(0, board.getField(3,3));
    }

    /**
     * Test of het bord de goede staat van een veld teruggeeft.
     * @throws Exception
     */
    @Test
    public void testGetField1() throws Exception {
        assertEquals(Board.EMPTY_FIELD, board.getField(positie));
    }

    /**
     * Test of het bord een veld kan vullen met een kleur.
     * @throws Exception
     */
    @Test
    public void testSetField() throws Exception {
        board.setField(5,5, speler.getColor());
        assertEquals(0, board.getField(positie));
    }

    /**
     * Test of het bord een veld kan vullen met een kleur.
     * @throws Exception
     */
    @Test
    public void testSetField1() throws Exception {
        board.setField(positie, speler.getColor());
        assertEquals(0, board.getField(positie));
    }

    /**
     * Test of het bord een leeg veld kan herkennen.
     * @throws Exception
     */
    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(board.isEmpty(5,5));
    }

    /**
     * Test of het bord een leeg veld kan herkennen.
     * @throws Exception
     */
    @Test
    public void testIsEmpty1() throws Exception {
        assertTrue(board.isEmpty(positie));
    }

    /**
     * Kijkt of het kopie van het bord gelijk is aan het bord.
     * @throws Exception
     */
    @Test
    public void testCopy() throws Exception {
        board.setField(positie,speler.getColor());
        Board copy = board.copy();
        board.equals(copy);
    }

    /**
     * Test of het bord slagen op een bepaald veld herkent.
     * @throws Exception
     */
    @Test
    public void testGetCapture() throws Exception {
        Capture capture = new Capture(new Position(-1, -1), 2);
        capture.equals(board.getCapture(speler.getColor(), positie));
    }

    /**
     * Test of het bord een legale zet kan herkennen.
     * @throws Exception
     */
    @Test
    public void testIsLegalMove() throws Exception {
        assertTrue(board.isLegalMove(speler.getColor(), positie));
    }

    /**
     * Kijkt of een zet gedaan kan worden.
     * @throws Exception
     */
    @Test
    public void testDoMove() throws Exception {
        board.doMove(speler.getColor(), positie);
        assertEquals(0, board.getField(positie));
    }

    /**
     * Kijkt of het het spel voorbij is.
     * @throws Exception
     */
    @Test
    public void testGameOver() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x ++){
                board.setField(x,y, speler.getColor());
            }
        }
        assertTrue(board.gameOver());
    }

    /**
     * Test of de goede highscore wordt berekent.
     * @throws Exception
     */
    @Test
    public void testGetHighScore() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x ++){
                board.setField(x,y, speler.getColor());
            }
        }
        assertEquals(64, board.getHighScore());
    }

    /**
     * Test of de goede winnaar gegeven wordt.
     * @throws Exception
     */
    @Test
    public void testDetermineWinners() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x ++){
                board.setField(x,y, speler.getColor());
            }
        }
        int i = board.determineWinners()[0];
        assertEquals(speler.getColor(), i);
    }
}
