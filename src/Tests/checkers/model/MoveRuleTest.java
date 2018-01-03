package checkers.model;

import checkers.model.MoveRule;
import core.model.Board;
import core.model.Command;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;



public class MoveRuleTest {
    MoveRule moveRule;
    Board board;
    Command command;

    @Before
    public void create(){
        moveRule = new MoveRule();
    }

    @Test
    public void checkValid() throws Exception {
        //TODO: (no usages)
        //assertEquals(false, moveRule.checkValid(board, command));
    }

    @Test
    public void getInvalidMsg() throws Exception {
        assertSame("wrong move", moveRule.getInvalidMsg());
    }

}