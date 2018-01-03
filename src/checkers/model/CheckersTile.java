package checkers.model;

import core.model.Tile;

public class CheckersTile extends Tile {

    //null if there is no piece on this place
    private CheckersPiece piece;
    private boolean schedule;
    private int x, y;

    public CheckersTile(boolean schedule, int x, int y) {
        this.schedule = schedule;
        this.x = x;
        this.y = y;
    }

    public boolean getAccessible() {
        return schedule;
    }

    public CheckersPiece getPiece() {
        //null if not
        return piece;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPiece(CheckersPiece piece)
    {
        this.piece = piece;
    }
    public String getCoords()
    {
        return x + "," + y;
    }
}
