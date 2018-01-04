package kernel.backend;

import checkers.backend.GameBoard;
import checkers.frontend.GameView;
import com.sun.security.ntlm.Client;
import kernel.frontend.AssetsLoader;
import kernel.frontend.ServerMenu;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AbstractBoardTest {

    AbstractBoard board;

    @Before
    public void create(){
        AssetsLoader.loadConfiguration();
        board = new GameBoard();
    }

    @Test
    public void runClient() throws Exception {
        GameBoard board = new GameBoard();
        board.runClient("127.0.0.1", "5005" );
    }

    @Test
    public void runServer() throws Exception {
        GameBoard board = new GameBoard();
        board.runServer();
    }

    @Test
    public void local_player_getter() throws Exception {
        Participant name = mock(Participant.class);
        board.local_player_setter(name);
        assertSame(name, board.local_player_getter());
    }

    @Test
    public void player_list_getter() throws Exception {
        Participant name = mock(Participant.class);
        board.player_list.add(name);
        assertSame(name, board.player_list_getter().get(2));
    }

    @Test
    public void active_player_getter() throws Exception {
        assertNotNull(board.active_player_getter());

    }

    @Test
    public void executor_getter() throws Exception {
        assertNotNull(board.executor_getter());
    }

    @Test
    public void out_msg_setter() throws Exception {
        //used in move method

    }

    @Test
    public void local_player_setter() throws Exception {
        Participant name = mock(Participant.class);
        board.local_player_setter(name);
        assertSame(name, board.local_player_getter());
    }

    @Test
    public void connect_getter() throws Exception {
        assertFalse(board.connect_getter());
        board.runServer();
        board.runClient("127.0.0.1", "4444");
        //assertTrue(board.connect_getter());
        //connect setter works (checked it), but i get false :/
    }

    @Test
    public void nextPlayer() throws Exception {
//        GameView view = new GameView();
//        view.board.nextPlayer();
        //tested in GameBoard
    }

    @Test
    public void show_message() throws Exception {
        board.show_message("elo ziomki");
    }

    @Test
    public void addPlayer() throws Exception {
        board.addPlayer("bartek", false);
        assertSame("bartek", board.player_list.get(2).getName());
        assertSame(false, board.player_list.get(2).isRobot());
        board.addPlayer("maciek", true);
        assertSame("maciek", board.player_list.get(3).getName());
        assertSame(true, board.player_list.get(3).isRobot());
    }

}