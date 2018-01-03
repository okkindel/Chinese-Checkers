package checkers.model;

import checkers.model.CheckersBoard;
import checkers.model.CheckersCommand;
import checkers.model.CheckersTile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class CheckersCommandTest {
    CheckersCommand command;
    CheckersBoard board;
    CheckersTile destinationTile;
    CheckersTile previousTile;

    @Before
    public void create(){
        board = mock(CheckersBoard.class);
        destinationTile = mock(CheckersTile.class);
        previousTile = mock(CheckersTile.class);
        command = new CheckersCommand(board, previousTile, destinationTile);
    }

    @Test
    public void getBoard() throws Exception {

        assertSame(board, command.getBoard());
    }

    @Test
    public void getPreviousTile() throws Exception {

        assertSame(previousTile, command.getPreviousTile());
    }

    @Test
    public void getDestinationTile() throws Exception {
        assertSame(destinationTile, command.getDestinationTile());
    }

}