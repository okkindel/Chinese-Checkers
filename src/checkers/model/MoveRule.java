package checkers.model;

import java.util.ArrayList;
import java.io.Serializable;

import core.model.Board;
import core.model.Command;
import core.model.Rule;

public class MoveRule implements Rule, Serializable {

    @Override
    public boolean checkValid(Board board, Command command)
    {
        //initially set the return value to false, since it hasn't yet been proven valid
        boolean returned = false;
        //casting the board and command, so that the checkers specific gets and methods can be used
        CheckersBoard checkersBoard = (CheckersBoard) board;
        CheckersCommand checkersCommand = (CheckersCommand) command;

        //this uses the same method as the checkers AI to retrieve a list of all valid moves from the seed tile
        ArrayList<CheckersTile[]> viableMoves = checkersBoard.findMoves(checkersCommand.getPreviousTile());

        //from here we simply need to check if the move is located in the returned list
        for (CheckersTile[] viableMove : viableMoves) {
            //note we only need to check the destination tile, since the source tile has already been used to get the list of viable moves
            if (viableMove[1] == checkersCommand.getDestinationTile()) {
                returned = true;
            }
        }
        return returned;
    }

    @Override
    public String getInvalidMsg() {
        return "wrong move";
    }
}
