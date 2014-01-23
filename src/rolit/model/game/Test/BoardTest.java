package rolit.model.game.Test;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA._PolicyStub;
import rolit.model.game.*;

import javax.print.attribute.standard.PrinterLocation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Martijn on 22-1-14.
 */
public class BoardTest {
    private Board board;
    private Position position;
    private Player player;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        position = new Position(5,5);
        player = new EasyComputerPlayer(0);
    }

    @Test
    public void testGetField() throws Exception {
        assertEquals(Board.EMPTY_FIELD, board.getField(position));
    }

    @Test
    public void testGetField1() throws Exception {
        assertEquals(Board.EMPTY_FIELD, board.getField(position));
    }

    @Test
    public void testSetField() throws Exception {
        board.setField(2,2,0);
        assertEquals(0,board.getField(2,2));
    }

    @Test
    public void testSetField1() throws Exception {
        board.setField(position, 0);
        assertEquals(0, board.getField(position));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(board.isEmpty(5,5));
    }

    @Test
    public void testIsEmpty1() throws Exception {
        assertTrue(board.isEmpty(position));
    }

    @Test
    public void testCopy() throws Exception {
        assertEquals(board, board.copy());
    }

    @Test
    public void testGetCapture() throws Exception {
        Capture capture = new Capture(new Position(-1,1), 1);
        Capture [] captures = board.getCapture(player, position);
        capture.equals(captures[0]);
    }

    @Test
    public void testIsLegalMove() throws Exception {
        Position position = new Position(5,5);
        assertTrue(board.isLegalMove(player, position));
    }

    @Test
    public void testDoMove() throws Exception {
        assertTrue(board.doMove(player, position));
    }

    @Test
    public void testGameOver() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x++){
                board.setField(x,y, 0);
            }
        }
        assertTrue(board.gameOver());
    }

    @Test
    public void testDetermineWinner() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x++){
                board.setField(x,y, 0);
            }
        }
        assertEquals(0, board.determineWinner());
    }

}
