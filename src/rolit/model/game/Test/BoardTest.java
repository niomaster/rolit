package rolit.model.game.Test;

import org.junit.Before;
import org.junit.Test;
import rolit.model.game.*;

import static junit.framework.Assert.*;

/**
 * De test die alle methoden test in board.
 * @author Martijn de Bijl
 */
public class BoardTest {
    private Board board;
    private Position position;
    private Player player;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        position = new Position(5,5);
        player = new EasyComputerPlayer();
        player.setColor(0);
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
    public void testSetField2() throws Exception {
        try {
            board.setField(new Position(9, 20), 0);
            fail();
        } catch(ArrayIndexOutOfBoundsException e) {

        }
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
    public void testGetCapture1() throws Exception {
        board.getCapture(player, new Position(-1,-1));
    }

    @Test
    public void testIsLegalMove() throws Exception {
        Position position = new Position(5,5);
        assertTrue(board.isLegalMove(player, position));
    }

    @Test
    public void testIsLegalMove1() throws Exception {
        Position position = new Position(6,6);
        assertFalse(board.isLegalMove(player, position));
    }

    @Test
    public void testIsLegalMove2() throws Exception {
        Position position = new Position(-1,-1);
        assertFalse(board.isLegalMove(player, position));
    }

    @Test
    public void testDoMove() throws Exception {
        assertTrue(board.doMove(player, position));
        assertEquals(0, board.getField(4,4));
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
    public void testGameOver1() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < (Board.BOARD_WIDTH - 1); x++){
                board.setField(x,y, 0);
            }
        }
        assertFalse(board.gameOver());
    }

    @Test
    public void testDetermineWinner() throws Exception {
        for (int y = 0; y < Board.BOARD_HEIGHT; y++){
            for (int x = 0; x < Board.BOARD_WIDTH; x++){
                board.setField(x,y, 0);
            }
        }
        assertEquals(1, board.determineWinners().length);
    }

    public void testDetermineWinner1() throws Exception {
        Integer[] winners = board.determineWinners();
        assertEquals(2, (int)winners[2]);
    }

}
