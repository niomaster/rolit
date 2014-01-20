package rolit.model.game;

/**
 * Created by Martijn on 20-1-14.
 */
public class Board {

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;
    private int[][] array;
    private static final int leegVeld = 9;

    public Board(){
        array = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x <= 8; x ++){
            for(int y = 0; x <= 8; x ++){
                array[x][y] = leegVeld;
                array[x][y] = leegVeld;
            }
        }
        array[3][3] = 0;
        array[4][3] = 1;
        array[4][4] = 2;
        array[3][4] = 3;

    }

    public int getField(int x, int y){
        return array[x][y];
    }

    public void setField(int x, int y, int field){
        array[x][y] = field;
    }

    public Board copy(){
        Board copy = new Board();
        for (int x = 0; x <= 8; x ++){
            for (int y = 0; y <= 8; y ++){
                copy.setField(x, y, getField(x, y));
            }
        }
        return copy;
    }

    public boolean isEmpty(int x, int y){
        if(array[x][y] == leegVeld){
            return true;
        }
        else {
            return false;
        }
    }

    public String toString(){
    String row = "";
    String column = "";
    String board = "";
    for(int j = 0; j <= 8; j ++){
        column = row + "\n";
        board = board + column;
        for(int i = 0; i <= 8; i++){
            row = "|" + array[i][j] + "|";
            }
        }
        return board;
    }

    public static void main(String[] args) {
        System.out.println(new Board().toString());
    }
}
