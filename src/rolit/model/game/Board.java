package rolit.model.game;

import rolit.view.client.MainView;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The board class
 *
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
     * Alle richtingen waarin gekeken moet worden naar aanliggende velden.
     */
    private static Position[] DIRECTIONS = {
            new Position(-1, -1),
            new Position(-1, 0),
            new Position(-1, 1),
            new Position(1, 0),
            new Position(1, -1),
            new Position(1, 1),
            new Position(0, 1),
            new Position(0, -1)
    };

    /**
     * Constructor voor klasse bord. Hierin wordt de array aangemaakt en gevuld met legen velden en de 4 balletje in
     * het midden.
     */
    public Board() {
        array = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                array[x][y] = EMPTY_FIELD;
                array[x][y] = EMPTY_FIELD;
            }
        }
        array[3][3] = 0;
        array[4][3] = 1;
        array[4][4] = 2;
        array[3][4] = 3;
    }

    /**
     * Geeft terug waarmee een bepaald veld gevuld is.
     *
     * @param x de x-coördinaat, oftewel horizontale positie op het bord.
     * @param y de y-coördinaat, oftwel vericale positie op het bord.
     * @return geeft een integer terug die de kleur van de speler, of een leeg veld aangeeft.
     */
    public int getField(int x, int y) {
        return array[x][y];
    }

    /**
     * Geeft terug waarmee een bepaald veld gevuld is.
     *
     * @param position de positie van het veld, gegeven in een vector met een x en y coördinaat.
     * @return geeft een integer terug die de kleur van de speler, of een leeg veld aangeeft.
     */
    public int getField(Position position) {
        int x = (position.getX());
        int y = (position.getY());
        return array[x][y];
    }

    /**
     * Vult een bepaald veld met een kleur van een speler.
     *
     * @param x     de x-coördinaat, oftewel horizontale positie op het bord.
     * @param y     de y-coördinaat, oftwel vericale positie op het bord.
     * @param field de integer die de kleur van de speler aangeeft.
     */
    public void setField(int x, int y, int field) {
        array[x][y] = field;
    }

    /**
     * Vult een bepaald veld met een kleur van een speler.
     *
     * @param position de positie van het veld, gegeven in een vector met een x en y coördinaat.
     * @param color    de integer die de kleur van de speler aangeeft.
     */
    public void setField(Position position, int color) {
        array[position.getX()][position.getY()] = color;
    }

    /**
     * Geeft terug of een veld leeg is.
     *
     * @param x de x-coördinaat, oftewel horizontale positie op het bord.
     * @param y de y-coördinaat, oftwel vericale positie op het bord.
     * @return een boolean of het veld leeg is.
     */
    public boolean isEmpty(int x, int y) {
        return array[x][y] == EMPTY_FIELD;
    }

    /**
     * Geeft terug of een veld leeg is.
     *
     * @param position de positie van het veld, gegeven in een vector met een x en y coördinaat.
     * @return een boolean of het veld leeg is.
     */
    public boolean isEmpty(Position position) {
        return array[position.getX()][position.getY()] == EMPTY_FIELD;
    }

    /**
     * Maakt een kopie van het huidige bord.
     *
     * @return een nieuw bord met alle informatie van het huidige bord.
     */
    public Board copy() {
        Board copy = new Board();
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                copy.setField(x, y, getField(x, y));
            }
        }
        return copy;
    }

    /**
     * Kijkt op een bepaald veld in alle richtingen welke slagen mogelijk zijn.
     *
     * @param player       de speler die de zet wil doen.
     * @param movePosition het veld waarop de speler de zet wil doen.
     * @return een array met alle slagen die mogelijk zijn.
     */
    public Capture[] getCapture(Player player, Position movePosition) {
        Position position = new Position(movePosition.getX(), movePosition.getY());
        LinkedList<Capture> captures = new LinkedList<Capture>();
        int length = 0;

        if (!this.isEmpty(position)) {
            Capture[] capture = new Capture[0];
            return capture;
        } else {
            for (Position direction : DIRECTIONS) {
                length = 1;

                Position checkField = new Position(position.add(direction).getX(), position.add(direction).getY());

                while (!checkField.outOfBounds() && this.getField(checkField) != player.getColor() && this.getField(checkField) != EMPTY_FIELD) {
                    checkField = checkField.add(direction);
                    length++;
                }

                if (!checkField.outOfBounds() && this.getField(checkField) == player.getColor() && length > 1) {
                    captures.add(new Capture(direction, length));
                }

            }

            Capture[] result = new Capture[captures.size()];
            captures.toArray(result);
            return result;
        }
    }

    /**
     * Kijkt of een zet legaal is.
     *
     * @param player       de speler die de zet wil doen.
     * @param movePosition het veld waarop de speler de zet wil doen.
     * @return een boolean of de zet legaal is.
     */
    public boolean isLegalMove(Player player, Position movePosition) {

        if (movePosition.outOfBounds()) {
            return false;
        }

        Capture[] captures = getCapture(player, movePosition);

        if (captures.length != 0) {
            return true;
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Position checkField = new Position(x, y);
                if (getCapture(player, checkField).length > 0) {
                    return false;
                }
            }
        }

        if (hasSurroundingFields(movePosition)){
            return true;
        }

        return true;
    }

    private boolean hasSurroundingFields(Position movePosition) {
        for (Position direction : DIRECTIONS){
            if (!movePosition.add(direction).outOfBounds()){
                if (getField(movePosition.add(direction)) != EMPTY_FIELD){
                    return true;
                }
            }
        }
       return false;
    }

    /**
     * Doet de zet van een speler, en verandert alle kleuren naar zijn kleur van de geslagen balletjes.
     *
     * @param player       de speler die de zet doet.
     * @param movePosition het veld waarop de speler de zet wil doen.
     * @return een boolean of de zet gelukt is.
     */
    public boolean doMove(Player player, Position movePosition) {
        if (isLegalMove(player, movePosition)) {
            Capture[] captures = getCapture(player, movePosition);
            setField(movePosition, player.getColor());

            for (Capture capture : captures) {
                for (int i = 0; i <= captures.length; i++) {
                    int x = (movePosition.getX() + i * (capture.getDirection().getX()));
                    int y = (movePosition.getY() + i * (capture.getDirection().getY()));
                    Position seize = new Position(x, y);
                    if (!seize.outOfBounds()) {
                        setField(seize, player.getColor());
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Maakt een visuele representatie van het huidige bord.
     *
     * @return een string zo geformat dat het bord leesbaar is.
     */
    public String toString() {
        String result = "+-+-+-+-+-+-+-+-+\n";
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            boolean first = true;

            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (first) {
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

    /**
     * Kijkt of alle velden gevuld zijn, dus of het spel is afgelopen.
     *
     * @return een boolean of het spel al voorbij is.
     */
    public boolean gameOver() {
        boolean gameOver = true;

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (getField(x, y) == EMPTY_FIELD) {
                    gameOver = false;
                }
            }
        }

        return gameOver;
    }

    /**
     * Bepaald de winneer van het spel, als het spel is afgelopen.
     *
     * @return een integer die de kleur van de winnaar representateerd.
     */
    public Integer[] determineWinners() {
        int rood = 0;
        int geel = 0;
        int groen = 0;
        int blauw = 0;

        if (gameOver() == true) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    if (getField(x, y) == 0) {
                        rood++;
                    } else if (getField(x, y) == 1) {
                        geel++;
                    } else if (getField(x, y) == 2) {
                        groen++;
                    } else {
                        blauw++;
                    }
                }
            }

        }

        int max = -1;
        List<Integer> result = new LinkedList<Integer>();
        int[] data = {rood, geel, groen, blauw};

        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
                result.clear();
                result.add(i);
            } else if (data[i] == max) {
                result.add(i);
            }
        }

        Integer[] arrayResult = new Integer[result.size()];
        result.toArray(arrayResult);
        return arrayResult;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Board) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    if (this.getField(x, y) != ((Board) object).getField(x, y)) {
                        return false;
                    }

                }
            }
            return true;
        } else {
            return false;
        }
    }
}
