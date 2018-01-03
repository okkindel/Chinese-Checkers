package core.model;

import checkers.model.CheckersBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTest {

    Board board;
    Player player;
    ArrayList<Player> players = new ArrayList<>();
    @Before
    public void create() {
        board = new CheckersBoard();
    }
    @Test
    public void getConnected() throws Exception {
        assertEquals(false, board.getConnected());
    }

    @Test
    public void getLocalPlayer() throws Exception {
        assertEquals(board.localPlayer, board.getLocalPlayer());
    }

    @Test
    public void getPlayers() throws Exception {
        board.addPlayer("mike", false);
        board.addPlayer("micki", true);

        assertEquals("mike",board.getPlayers().get(0).getName());
        assertEquals("micki",board.getPlayers().get(1).getName());

        boolean czlek = false;
        if(board.getPlayers().get(0) instanceof Human)
            czlek = true;

        assertEquals(true, czlek);
        czlek = true;
        if(board.getPlayers().get(1) instanceof AI)
            czlek = false;
        assertEquals(false, czlek);
    }

    @Test
    public void addPlayer() throws Exception {
        player = new Human("mike");
        players.add(player);

        board.addPlayer("mike", false);
        board.addPlayer("micki", true);

        assertEquals("mike",board.getPlayers().get(0).getName());
        assertEquals("micki",board.getPlayers().get(1).getName());

        boolean czlek = false;
        if(board.getPlayers().get(0) instanceof Human)
            czlek = true;

        assertEquals(true, czlek);
        czlek = true;
        if(board.getPlayers().get(1) instanceof AI)
            czlek = false;
        assertEquals(false, czlek);

    }

}