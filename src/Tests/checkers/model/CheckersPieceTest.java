package checkers.model;

import checkers.model.CheckersPiece;
import core.model.Player;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckersPieceTest {
    CheckersPiece piece;
    Player owner;

    @Test
    public void getOwner() throws Exception {
        piece = mock(CheckersPiece.class);
        owner = mock(Player.class);
        when(piece.getOwner()).thenReturn(owner);
    }

}