package checkers.backend;

import java.util.ArrayList;
import java.io.Serializable;

import kernel.backend.AbstractBoard;
import kernel.backend.RuleInterface;
import kernel.backend.UndoInterface;

/**
 * Some moving rulezzzz.
 */
public class MovingRules implements RuleInterface, Serializable {

    @Override
    public String retarded_move() {
        return "Wrong move asshole!";
    }

    @Override
    public boolean checkValid(AbstractBoard abstractBoard, UndoInterface undoInterface) {
        //here clever code :3
        //false, since it hasn't yet been proven valid
        boolean correct = false;
        //we need our getters and setters
        //so here we have them
        GameBoard gameBoard = (GameBoard) abstractBoard;
        GameCommands gameCommands = (GameCommands) undoInterface; //here we have getters too
        //all valid moves
        ArrayList<GameField[]> viableMoves = gameBoard.findMoves(gameCommands.getPrevious_area());

        //if the move is located in the returned list
        for (GameField[] viableMove : viableMoves) {
            //only check the destination tile, prev has been used to get the list of viable moves
            if (viableMove[1] == gameCommands.getDestiny_area()) {
                correct = true;
            }
        }
        return correct;
    }
}
