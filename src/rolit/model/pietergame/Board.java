package rolit.model.pietergame;

import rolit.common.Position;

import java.util.LinkedList;

public class Board {
    public static int WIDTH = 8;
    public static int HEIGHT = 8;

    public static int NO_PLAYER = -1;

    private static Position[] DIRECTIONS = {
        new Position(-1, -1),
        new Position(0, -1),
        new Position(1, -1),
        new Position(-1, 0),
        new Position(1, 0),
        new Position(-1, 1),
        new Position(0, 1),
        new Position(1, 1),
    };

    private int[][] board = new int[WIDTH][HEIGHT];

    public Board() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                board[x][y] = NO_PLAYER;
            }
        }

        int middleX = WIDTH / 2 - 1;
        int middleY = HEIGHT / 2 - 1;

        board[middleX][middleY] = 0;
        board[middleX + 1][middleY] = 1;
        board[middleX][middleY + 1] = 2;
        board[middleX + 1][middleY + 1] = 3;
    }

    public int get(Position position) {
        return board[position.getX()][position.getY()];
    }

    private void set(Position position, int player) {
        board[position.getX()][position.getY()] = player;
    }

    public Capture[] getCaptures(int player, Position movePosition) {
        LinkedList<Capture> captures = new LinkedList<Capture>();

        for(Position direction : DIRECTIONS) {
            for(int length = Math.max(WIDTH, HEIGHT); length >= 2; length--) {
                Position position = movePosition.add(direction.scalarMultiply(length));

                if(position.inRectangle(WIDTH, HEIGHT) && get(position) == player) {
                    boolean anyOther = false;
                    boolean filled = true;

                    for(int i = 1; i < length; i++) {
                        Position innerPosition = movePosition.add(direction.scalarMultiply(i));

                        if(innerPosition.inRectangle(WIDTH, HEIGHT)) {
                            if(get(innerPosition) == NO_PLAYER) {
                                filled = false;
                            } else if(get(innerPosition) != player) {
                                anyOther = true;
                            }
                        }
                    }

                    if(filled && anyOther) {
                        captures.add(new Capture(direction, length));
                        break;
                    }
                }
            }
        }

        Capture[] result = new Capture[captures.size()];
        captures.toArray(result);
        return result;
    }

    public boolean isValidMove(int player, Position movePosition) {
        boolean adjacent = false;

        for(Position direction : DIRECTIONS) {
            Position position = movePosition.add(direction);

            if(position.inRectangle(WIDTH, HEIGHT) && get(position) != NO_PLAYER) {
                adjacent = true;
                break;
            }
        }

        if(!adjacent) {
            return false;
        }

        // Ensured that the move is adjacent to any other ball

        if(getCaptures(player, movePosition).length > 0) {
            return true;
        }

        // Player didn't do a block and capture

        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                Position currentPosition = new Position(x, y);

                if(get(currentPosition) != NO_PLAYER) {
                    continue;
                }

                if(getCaptures(player, movePosition).length > 0) {
                    // Block and capture was possible, but player didn't do it
                    return false;
                }
            }
        }

        // Block and capture impossible, so the move is valid
        return true;
    }

    public boolean doMove(int player, Position movePosition) {
        if(isValidMove(player, movePosition)) {
            Capture[] captures = getCaptures(player, movePosition);

            set(movePosition, player);

            for(Capture capture : captures) {
                for(int i = 1; i < capture.getLength(); i++) {
                    Position position = movePosition.add(capture.getDirection().scalarMultiply(i));
                    set(position, player);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String result = "";

        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                result += " 0123".charAt(board[x][y] + 1);
            }

            result += "\n";
        }

        return result;
    }

    public static void main(String[] args) {
        Board b = new Board();

        System.out.print(b);

        System.out.println(b.doMove(2, new Position(5, 2)));

        System.out.print(b);
    }
}
