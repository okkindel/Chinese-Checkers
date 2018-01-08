package checkers.backend;

import kernel.backend.AbstractBoard;
import kernel.backend.UndoInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MovingRulesTest {
    MovingRules rules;
    AbstractBoard aboard;
    AbstractBoard board;
    UndoInterface commands;


    @Before
    public void create(){
        rules = new MovingRules();
    }

    @Test
    public void retarded_move() throws Exception {
        assertEquals("Wrong move asshole!", rules.retarded_move());
    }

    @Test
    public void checkValid() throws Exception {
        //TODO:
        aboard = mock(GameBoard.class);
        commands = mock(GameCommands.class);
        assertEquals(false, rules.checkValid(aboard, commands));
    }

}