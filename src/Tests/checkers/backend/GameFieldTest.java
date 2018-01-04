package checkers.backend;

import kernel.backend.Participant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameFieldTest {
    GameField fieldTrue;
    GameField fieldFalse;
    GamePawn pawn;

    @Before
    public void create(){
        fieldTrue = new GameField(true, 5, 10);

    }

    @Test
    public void available_getter() throws Exception {
        fieldFalse = new GameField(false, 4, 4);
        assertEquals(true , fieldTrue.available_getter());
        assertEquals(false, fieldFalse.available_getter());
    }

    @Test
    public void getPawn() throws Exception {
        Participant owner = mock(Participant.class);
        pawn = new GamePawn(owner);
        fieldTrue.pawn_setter(pawn);
        assertSame(pawn, fieldTrue.getPawn());
    }

    @Test
    public void coordinates_getter() throws Exception {
        assertEquals("5,10", fieldTrue.coordinates_getter());
    }

    @Test
    public void pawn_setter() throws Exception {
        Participant owner = mock(Participant.class);
        pawn = new GamePawn(owner);
        fieldTrue.pawn_setter(pawn);
        assertSame(pawn, fieldTrue.getPawn());
    }

    @Test
    public void getX() throws Exception {
        assertEquals(5, fieldTrue.getX());
    }

    @Test
    public void getY() throws Exception {
        assertEquals(10, fieldTrue.getY());
    }

}