package checkers.frontend;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import checkers.backend.GameBoard;
import kernel.frontend.GameMenu;
import kernel.backend.AbstractBoard;
import kernel.frontend.AbstractView;

/**
 * GameView Class
 * Includes menus etc.
 */
public class GameView extends AbstractView {

    private BoardView boardView;
    private GameBoard board;

    /**
     * Where is not online game.
     */
    public GameView() {
        board = new GameBoard();
        boardView = new BoardView(board);
        paint();
    }

    /**
     * Where is online game.
     */
    public GameView(AbstractBoard abstractBoard) {
        this.board = (GameBoard) abstractBoard;
        boardView = new BoardView(this.board);
        paint();
    }

    /**
     * Listener for moves.
     */
    private class Moves implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.showAvailableMoves();
        }
    }

    /**
     * Listener for undo.
     */
    private class Undo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            boardView.getBoard().executor_getter().undo(); //undo using the command invoker
            boardView.repaint(); //then repaint, as the status of the backend has changed
        }

    }

    /**
     * Listener for new game.
     */
    private class NewGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            window.dispose();
            GameMenu gameMenu = new GameMenu();
            gameMenu.play();
        }

    }

    /**
     * Game menu and checkers area.
     */
    @Override
    public void paint() {
        super.paint();
        boardView.setPreferredSize(new Dimension(700, 645));
        window.add(boardView);
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game Menu");
        window.setJMenuBar(menuBar);
        menuBar.add(menu);
        if (board.local_player_getter() == null) {
            JMenuItem newGame = new JMenuItem("Naw Game");
            newGame.addActionListener(new NewGame());
            menu.add(newGame);
            JMenuItem undo = new JMenuItem("Undo");
            undo.addActionListener(new Undo());
            menu.add(undo);
        }
        JMenuItem hint = new JMenuItem("Show All Moves");
        hint.addActionListener(new Moves());
        menu.add(hint);
        JMenuItem quit = new JMenuItem("Exit");
        quit.addActionListener(new QuitButtonListener());
        menu.add(quit);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
