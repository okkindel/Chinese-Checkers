package checkers.backend;

import kernel.backend.Participant;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GamePawnTest {

    @Test
    public void getOwner() throws Exception {
        Participant owner = mock(Participant.class );
        GamePawn pawn = new GamePawn(owner);

        assertSame(owner, pawn.getOwner());
    }

}