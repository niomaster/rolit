package rolit.model.game;

import rolit.model.pietergame.Position;

import java.util.LinkedList;

/**
 * The board class
 * @author Martijn de Bijl
 */
public class Board {
    /**
     * De standaard hoogte voor het bord.
     */
    public static final int BOARD_WIDTH = 8;
    /**
     * De standaard breedte van het bord.
     */
    public static final int BOARD_HEIGHT = 8;
    /**
     * De variabele die een leeg veld aangeeft.
     */
    public static final int EMPTY_FIELD = 9;

    /**
     * De array waarin de informatie van het bord is opgeslagen.
     */
    private int[][] array;

    /**
     *
     */
    private static Vector[] DIRECTIONS = {
            new Vector(-1, -1),
            new Vector(-1, 0),
            new Vector(-1,1),
            new Vector(1,0),
            new Vector(1,-1),
            new Vector(1,1),
            new Vector(0,1),
            new Vector(0,-1)
    };

    public Board(){
        array = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++){
            for(int y = 0; y < BOARD_HEIGHT; y++){
                array[x][y] = EMPTY_FIELD;
                array[x][y] = EMPTY_FIELD;
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

    public int getField(Vector position){
        int x = (position.getX());
        int y = (position.getY());
        return array[x][y];
    }

    public void setField(int x, int y, int field){
        array[x][y] = field;
    }

    public Capture[] getCapture(Player player, Vector movePosition) {
        Vector position = new Vector(movePosition.getX(), movePosition.getY());
        LinkedList<Capture> captures = new LinkedList<Capture>();
        int length = 0;

        for (Vector direction : DIRECTIONS){
            Vector checkField = new Vector(position.add(direction).getX(), position.add(direction).getY());
            while (this.getField(checkField) != player.getColor() && this.getField(checkField) != EMPTY_FIELD) {
                checkField = checkField.add(direction);
                length ++;
            }
            if (this.getField(checkField.add(direction)) == player.getColor() && length > 1) {
                captures.add(new Capture(checkField, length));
            }
        }

        Capture[] result = new Capture[captures.size()];
        captures.toArray(result);
        return result;
    }

    public Board copy(){
        Board copy = new Board();
        for (int x = 0; x <= BOARD_WIDTH; x ++){
            for (int y = 0; y <= BOARD_HEIGHT; y ++){
                copy.setField(x, y, getField(x, y));
            }
        }
        return copy;
    }

    public boolean isEmpty(int x, int y){
        if(array[x][y] == EMPTY_FIELD){
            return true;
        }
        else {
            return false;
        }
    }

    public String toString(){
        String result = "+-+-+-+-+-+-+-+-+\n";
        for(int y = 0; y < BOARD_HEIGHT; y++) {
            boolean first = true;

            for(int x = 0; x < BOARD_WIDTH; x++) {
                if(first) {
                    result += "|";
                }

                first = false;

                result += getField(x, y) == EMPTY_FIELD ? " " : getField(x, y);

                result += "|";
            }

            result += "\n+-+-+-+-+-+-+-+-+\n";
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Board().toString());
    }
}
