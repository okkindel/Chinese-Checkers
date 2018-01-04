package checkers.backend;

import checkers.frontend.BoardView;
import checkers.frontend.GameView;
import kernel.frontend.AssetsLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    //GameBoard board;
    GameView view;
    GamePawn pawn;

    class Board extends GameBoard{
        boolean b;
        @Override
        public void w8_4_move(){
            show_message("Waiting for opponent move");
            int counter=0;
            b = false;
            if (activePlayer != local_player) {
                do {
                    while (msg_in == null) {
                        counter+=1;
                        if(counter>10000000) {
                            b = true;
                            break;

                        }

                        /*message is received and we need to parse it*/
                    }
                    if(b)
                        return;

                    String[] split = msg_in.split(",");
                    GameField previous = getTiles()[Integer.parseInt(split[0])][Integer.parseInt(split[1])];
                    GameField destiny = getTiles()[Integer.parseInt(split[2])][Integer.parseInt(split[3])];
                    msg_in = null;
                    move(previous, destiny);
                    return;
                } while (activePlayer != local_player);
            }
        }
    }

    class View extends GameView{
        Board boad;
        View(){
            boad = new Board();
            boardView = new BoardView(boad);
            paint();
        }
    }


    @Before
    public void create(){
        AssetsLoader.loadConfiguration();
        view = new GameView();
        view.setVisible(false);
    }

    @Test
    public void getTiles() throws Exception {
        boolean t = false;
        if(view.board.getTiles()[6][0].getPawn() != null){
            t = true;
        }
        assertEquals(true, t);
        assertNotSame(view.board.getTiles()[0][0].getPawn(),view.board.getTiles()[6][0].getPawn());
    }

    @Test
    public void move() throws Exception {
        pawn = view.board.getTiles()[6][3].getPawn();
        //System.out.println(pawn.getOwner());
        view.board.move(view.board.getTiles()[6][3], view.board.getTiles()[6][3]);
        assertNotSame(view.board.getTiles()[6][3].getPawn(), view.board.getTiles()[6][4].getPawn());
        //view.board.w8_4_move();

    }

    @Test
    public void findMoves() throws Exception {
        view.board.nextPlayer();
        boolean t =false;
        if(view.board.getTiles()[3][12].getPawn() != null ||
                view.board.getTiles()[4][12].getPawn() != null ||
                view.board.getTiles()[5][12].getPawn() != null ||
                view.board.getTiles()[6][12].getPawn() != null ||
                view.board.getTiles()[7][12].getPawn() != null ||
                view.board.getTiles()[8][12].getPawn() != null){
            t = true;
        }
        assertSame(true, t);
    }

    @Test
    public void w8_4_move() throws Exception {
        view.board.nextPlayer();
        View vi = new View();
        vi.boad.w8_4_move();
        assertTrue(vi.boad.b);
    }

    @Test
    public void nextPlayer() throws Exception {
        view.board.nextPlayer();
        assertSame(view.board.activePlayer.getName(),view.board.player_list_getter().get(0).getName());
    }

}