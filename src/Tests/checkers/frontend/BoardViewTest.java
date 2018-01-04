package checkers.frontend;

import checkers.backend.GameBoard;
import checkers.backend.GameField;
import checkers.backend.GamePawn;
import kernel.frontend.AssetsLoader;
import kernel.frontend.GameMenu;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardViewTest {
    BoardView boardView;
    GameMenu gameMenu;
    GameView gameView;

    @Test
    public void getBoard() throws Exception {
        GameBoard board = mock(GameBoard.class);
        boardView = new BoardView(board);
        assertEquals(board, boardView.getBoard());
    }

    @Test
    public void update() throws Exception {
        //consists awt methods which will work without problems
        gameMenu = new GameMenu();
        gameMenu.setVisible(false);

        AssetsLoader.loadConfiguration();
        gameView = new GameView();
    }

    @Test
    public void paint() throws Exception {
//        gameMenu = new GameMenu();
//        gameMenu.setVisible(false);

        AssetsLoader.loadConfiguration();
        gameView = new GameView();
        gameView.boardView.drawSelected(6, 0);
    }

    @Test
    public void showAvailableMoves() throws Exception {
        AssetsLoader.loadConfiguration();
        gameView = new GameView();
        gameView.boardView.showAvailableMoves();
    }

}