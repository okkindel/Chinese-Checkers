package checkers.backend;

import kernel.backend.UndoInterface;

/**
 * GameCommands Class
 * I mean undo lel :F
 * AAAAND executing move of course
 */
public class GameCommands implements UndoInterface {

    /**
     * important as i think
     * generally really important
     **/
    @Override
    public void execute() {
        piece = previous_area.getPawn();
        previous_area.pawn_setter(null);
        destiny_area.pawn_setter(piece);
    }

    private GameField previous_area;
    private GameField destiny_area;

    private GamePawn piece;

    GameField getPrevious_area() {
        return previous_area;
    }

    GameField getDestiny_area() {
        return destiny_area;
    }

    /**
     * Constructor
     */
    GameCommands(GameField previous_area, GameField destiny_area) {
        this.previous_area = previous_area;
        this.destiny_area = destiny_area;
    }

    /**
     * Undo...
     */
    @Override
    public void undo() {
        piece = destiny_area.getPawn();
        destiny_area.pawn_setter(null); //remove pawn
        previous_area.pawn_setter(piece);
    }
}