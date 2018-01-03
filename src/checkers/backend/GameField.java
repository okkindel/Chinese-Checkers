package checkers.backend;

import kernel.backend.AbstractField;

/**
 * GameField Class
 */
public class GameField extends AbstractField {

    boolean available_getter() {
        return available;
    }

    public GamePawn getPawn() {
        return piece;
    }

    /**
     * Return coordinates as a string.
     *
     * @returns x and y coordinates, with , as separator
     */
    String coordinates_getter() {
        return x + "," + y;
    }

    void pawn_setter(GamePawn piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private GamePawn piece;
    private boolean available;
    private int x, y;

    GameField(boolean available, int x, int y) {
        this.available = available;
        this.x = x;
        this.y = y;
    }
}
