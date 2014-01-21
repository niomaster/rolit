package rolit.model.game;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Martijn on 21-1-14.
 */
public class EasyComputerPlayer extends Player {

    private final int color;

    public EasyComputerPlayer(int nummer) {
        super(nummer);
        this.color = nummer;
    }

    public Position determineMove(Board board) {
        LinkedList<Position> possibleMoves = new LinkedList<Position>();
        for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                Position position = new Position(x, y);
                if (board.isLegalMove(this, position)) {
                    possibleMoves.add(position);
                }
            }
        }
        Random random = new Random();
        Position randomPosition = possibleMoves.get(random.nextInt(possibleMoves.size()));
        return randomPosition;
    }

    public void doMove(Board board, Position move) {
        board.doMove(this, determineMove(board));
    }

}
