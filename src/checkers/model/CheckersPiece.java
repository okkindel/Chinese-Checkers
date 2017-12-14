package checkers.model;

import core.model.Piece;
import core.model.Player;

public class CheckersPiece extends Piece {
    private Player owner;
    protected CheckersPiece(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
