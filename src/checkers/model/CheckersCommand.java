package checkers.model;

public class CheckersCommand {



    //the board on which the actions have been performed
    private CheckersTile previousTile;
    //the tile from which the piece is to be moved from
    private CheckersTile destinationTile;
    //the tile from which the piece is to be moved to
    private CheckersPiece piece;
    //the piece to move
    private CheckersBoard board;

    protected CheckersCommand(CheckersBoard board, CheckersTile previousTile, CheckersTile destinationTile)
    {
        //set the fields to the parameters given
        this.board = board;
        this.previousTile = previousTile;
        this.destinationTile = destinationTile;
    }

    public CheckersBoard getBoard()
    {
        return board;
    }
    public CheckersTile getPreviousTile()
    {
        return previousTile;
    }
    public CheckersTile getDestinationTile()
    {
        return destinationTile;
    }
}
